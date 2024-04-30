package sg.security.api.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.security.api.constant.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserNotFoundExceptionTest {

    @Nested
    class UserNotFound {

        @Test
        void userNotFoundException() {

            final UserNotFoundException exception = new UserNotFoundException();
            assertNotNull(exception);

        }

        @Test
        void userNotFoundExceptionResponseInteger() {

            final UserNotFoundException exception = new UserNotFoundException(1);
            assertNotNull(exception);
            assertEquals(exception.getMessage(), Errors.USER_NOT_FOUND + 1);

        }

        @Test
        void userNotFoundExceptionResponseString() {

            final UserNotFoundException exception = new UserNotFoundException("user");
            assertNotNull(exception);
            assertEquals(exception.getMessage(), Errors.USERNAME_NOT_FOUND.concat("user"));

        }

    }

}
