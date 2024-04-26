package sg.security.api.event;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sg.security.api.dto.user.User;
import sg.security.api.service.email.EmailVerificationService;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@AllArgsConstructor
public class EmailEventImpl implements ApplicationListener<EmailEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailEventImpl.class);

    private static final String URL_EMAIL_VERIFICATION = "/auths/email/verification?token=";

    private final @NonNull JavaMailSender mailSender;

    private final @NonNull EmailVerificationService emailVerificationService;


    @Async
    @Override
    public void onApplicationEvent(EmailEvent event) {

        User user = event.getUser();

        String verificationToken = UUID.randomUUID().toString();

        emailVerificationService.saveEmailVerification(user, verificationToken);

        String url = event.getApplicationUrl() + URL_EMAIL_VERIFICATION + verificationToken;


        try {

            sendEmailVerification(user, url);

        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.error("Error: {}" + e.getMessage());
        }

        LOGGER.info("Click the link to verify your registration: {}", url);
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

        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("info@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);

        mailSender.send(message);
    }

}



