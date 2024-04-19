package sg.security.api.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException() {
    }

    public RoleNotFoundException(final Integer object) {
        super("Role not found: " + object);
    }

    public RoleNotFoundException(final String object) {
        super("Role not found: ".concat(object));
    }
}
