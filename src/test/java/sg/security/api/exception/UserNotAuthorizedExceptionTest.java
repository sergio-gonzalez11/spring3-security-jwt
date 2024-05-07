package sg.security.api.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserNotAuthorizedExceptionTest {


    @Test
    void userNotFoundException() {

        final UserNotAuthorizedException exception = new UserNotAuthorizedException();
        assertNotNull(exception);

    }

}
