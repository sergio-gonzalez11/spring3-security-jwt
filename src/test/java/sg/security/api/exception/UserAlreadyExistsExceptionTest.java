package sg.security.api.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.security.api.constant.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserAlreadyExistsExceptionTest {

    @Nested
    class UserAlreadyExists {

        @Test
        void userAlreadyExistsException() {

            final UserAlreadyExistsException exception = new UserAlreadyExistsException();
            assertNotNull(exception);

        }

        @Test
        void userAlreadyExistsResponseString() {

            final UserAlreadyExistsException exception = new UserAlreadyExistsException("user");
            assertNotNull(exception);
            assertEquals(exception.getMessage(), Errors.USER_ALREADY_EXISTS.concat("user"));

        }

    }

}
