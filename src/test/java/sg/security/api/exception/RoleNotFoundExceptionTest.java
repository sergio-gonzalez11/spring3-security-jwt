package sg.security.api.exception;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.security.api.constant.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RoleNotFoundExceptionTest {

    @Nested
    class RoleNotFound {

        @Test
        void roleNotFoundException() {

            final RoleNotFoundException exception = new RoleNotFoundException();
            assertNotNull(exception);

        }

        @Test
        void roleNotFoundExceptionResponseInteger() {

            final RoleNotFoundException exception = new RoleNotFoundException(1);
            assertNotNull(exception);
            assertEquals(exception.getMessage(), Errors.ROLE_NOT_FOUND + 1);

        }

        @Test
        void roleNotFoundExceptionResponseString() {

            final RoleNotFoundException exception = new RoleNotFoundException("role");
            assertNotNull(exception);
            assertEquals(exception.getMessage(), Errors.ROLE_NOT_FOUND.concat("role"));

        }

    }

}
