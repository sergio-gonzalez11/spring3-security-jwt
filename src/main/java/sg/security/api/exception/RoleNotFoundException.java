package sg.security.api.exception;

import sg.security.api.constant.Errors;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(final Integer object) {
        super(Errors.ROLE_NOT_FOUND + object);
    }

    public RoleNotFoundException(final String name) {
        super(Errors.ROLE_NOT_FOUND.concat(name));
    }
}
