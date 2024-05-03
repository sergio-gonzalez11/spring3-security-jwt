package sg.security.api.exception;

public class EmailVerifyException extends RuntimeException {

    public EmailVerifyException(String message) {
        super(message);
    }
}
