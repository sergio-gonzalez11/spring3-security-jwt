package sg.security.api.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.security.api.constant.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class EmailVerificationNotFoundExceptionTest {

    @Nested
    class EmailVerificationNotFound {

        @Test
        void emailVerificationNotFoundException() {

            final EmailVerificationNotFoundException exception = new EmailVerificationNotFoundException();
            assertNotNull(exception);

        }

        @Test
        void emailVerificationNotFoundExceptionResponseString() {

            final EmailVerificationNotFoundException exception = new EmailVerificationNotFoundException("email@gmail.com");
            assertNotNull(exception);
            assertEquals(exception.getMessage(), Errors.EMAIL_NOT_FOUND.concat("email@gmail.com"));

        }

    }

}
