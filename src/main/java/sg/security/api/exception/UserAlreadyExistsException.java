package sg.security.api.exception;

import sg.security.api.constant.Errors;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message) {
        super(Errors.USER_ALREADY_EXISTS.concat(message));
    }
}
