package sg.security.api.exception;

import sg.security.api.constant.Errors;

public class EmailVerificationNotFoundException extends RuntimeException {

    public EmailVerificationNotFoundException() {
        super();
    }

    public EmailVerificationNotFoundException(final String object) {
        super(Errors.EMAIL_NOT_FOUND.concat(object));
    }
}
