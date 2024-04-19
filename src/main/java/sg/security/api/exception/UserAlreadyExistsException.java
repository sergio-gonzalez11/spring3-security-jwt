package sg.security.api.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super("User already exists: " + message);
    }
}
