package sg.security.api.exception;

import sg.security.api.constant.Errors;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(final Integer object) {
        super(Errors.USER_NOT_FOUND + object);
    }

    public UserNotFoundException(final String username) {
        super(Errors.USERNAME_NOT_FOUND.concat(username));
    }

}
