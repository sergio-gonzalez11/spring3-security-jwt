package sg.security.api.exception;

import sg.security.api.constant.Errors;

public class EmailVerifyException extends RuntimeException {

    public EmailVerifyException() {
        super(Errors.ACCOUNT_VERIFIED);
    }
}
