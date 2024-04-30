package sg.security.api.exception;

import org.springframework.security.core.AuthenticationException;
import sg.security.api.constant.Errors;

public class UserNotAuthorizedException extends AuthenticationException {
    public UserNotAuthorizedException() {
        super(Errors.USER_NOT_AUTHORIZED);
    }

}
