package sg.security.api.exception;

import sg.security.api.constant.Errors;

public class EmailVerifyException extends RuntimeException {

    public EmailVerifyException(String message) {
        super(message);
    }
}
