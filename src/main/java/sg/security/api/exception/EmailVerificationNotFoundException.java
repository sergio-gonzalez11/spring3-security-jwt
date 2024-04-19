package sg.security.api.exception;

public class EmailVerificationNotFoundException extends RuntimeException {

    public EmailVerificationNotFoundException(final String object) {
        super("Email not found: ".concat(object));
    }
}
