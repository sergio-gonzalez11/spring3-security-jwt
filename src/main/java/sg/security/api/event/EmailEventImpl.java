package sg.security.api.event;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sg.security.api.dto.auth.EmailVerification;
import sg.security.api.dto.user.User;
import sg.security.api.service.email.EmailVerificationService;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

import static sg.security.api.constant.Constants.*;

@Slf4j
@Component
@AllArgsConstructor
public class EmailEventImpl implements ApplicationListener<EmailEvent> {

    private final @NonNull JavaMailSender mailSender;

    private final @NonNull EmailVerificationService emailVerificationService;


    @Async
    @Override
    public void onApplicationEvent(EmailEvent event) {

        User user = event.getUser();

        String verificationToken = UUID.randomUUID().toString();

        EmailVerification emailVerification = EmailVerification.builder()
                .token(verificationToken)
                .createdAt(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusMinutes(EXPIRATION_TIME))
                .user(user)
                .build();

        emailVerificationService.saveEmailVerification(emailVerification);

        String url = getEventFormatUrl(event.getApplicationUrl(), verificationToken);

        try {

            sendEmailVerification(user, url);

        } catch (RuntimeException | MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Error sending email: {}", e);
        }

        log.info("Click the link to verify your registration: {}", url);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }


    public void sendEmailVerification(User user, String url) throws MessagingException, UnsupportedEncodingException {

        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, " + user.getFirstname() + ", </p>" +
                "<p>Thank you for registering with us," + "" +
                "Please, follow the link below to complete your registration.</p>" +
                "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                "<p> Thank you <br> Users Registration Portal Service";


        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        messageHelper.setFrom(INFO_EMAIL_CONTACT, senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);

        mailSender.send(message);
    }

    private String getEventFormatUrl(String applicationUrl, String token) {
        return applicationUrl + URL_EMAIL_VERIFICATION + token;
    }
}



