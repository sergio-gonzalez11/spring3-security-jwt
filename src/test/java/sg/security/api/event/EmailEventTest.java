package sg.security.api.event;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import sg.security.api.data.dto.UserData;
import sg.security.api.dto.auth.EmailVerification;
import sg.security.api.dto.user.User;
import sg.security.api.service.email.EmailVerificationService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailEventTest {

    @Mock
    EmailVerificationService emailVerificationService;

    @Mock
    HttpServletRequest request;

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailEventImpl emailEvent;

    UserData userData;

    String token;

    EmailEvent event;


    @BeforeEach
    void setUp() {
        this.userData = new UserData();
        token = UUID.randomUUID().toString();
        event = new EmailEvent(userData.get(1), request.toString());
    }

    @Test
    void asyncExecutionIsTrueByDefault() {
        assertTrue(emailEvent.supportsAsyncExecution());
    }

    @Nested
    class Email {

        @Test
        void emailEventIsOk() {

            doNothing().when(EmailEventTest.this.emailVerificationService).saveEmailVerification(any(EmailVerification.class));
            when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

            emailEvent.onApplicationEvent(event);

            verify(emailVerificationService).saveEmailVerification(any(EmailVerification.class));
            verify(javaMailSender).createMimeMessage();

        }

        @Test
        void emailEventIsException() {

            doNothing().when(EmailEventTest.this.emailVerificationService).saveEmailVerification(any(EmailVerification.class));
            when(javaMailSender.createMimeMessage()).thenReturn(null);

            assertThrows(RuntimeException.class, () -> EmailEventTest.this.emailEvent.onApplicationEvent(event));

            verify(javaMailSender).createMimeMessage();

        }

    }


}
