package sg.security.api.exception;

import sg.security.api.constant.Errors;

public class EmailVerificationExpiredException extends RuntimeException {

    public EmailVerificationExpiredException() {
    }

    public EmailVerificationExpiredException(final String email) {
        super(Errors.EMAIL_VERIFICATION_EXPIRED.concat(email));
    }
}
