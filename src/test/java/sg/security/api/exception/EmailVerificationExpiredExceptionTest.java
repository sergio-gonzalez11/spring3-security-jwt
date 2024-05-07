package sg.security.api.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.security.api.constant.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EmailVerificationExpiredExceptionTest {

    @Nested
    class EmailVerificationExpired {

        @Test
        void emailVerificationExpiredExceptionException() {

            final EmailVerificationExpiredException exception = new EmailVerificationExpiredException();
            assertNotNull(exception);

        }

        @Test
        void emailVerificationExpiredExceptionResponseString() {

            final EmailVerificationExpiredException exception = new EmailVerificationExpiredException("email@gmail.com");
            assertNotNull(exception);
            assertEquals(exception.getMessage(), Errors.EMAIL_VERIFICATION_EXPIRED.concat("email@gmail.com"));

        }

    }

}
