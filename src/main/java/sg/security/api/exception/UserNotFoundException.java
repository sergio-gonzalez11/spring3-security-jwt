package sg.security.api.exception;

public class UserNotFoundException extends RuntimeException {


    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundException(final Integer object) {
        super("User not found: " + object);
    }

    public UserNotFoundException(final String object) {
        super("User not found: ".concat(object));
    }

}
