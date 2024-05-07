package sg.security.api.constant;

public final class Errors {

    public static final String USER_NOT_AUTHORIZED = "User not authorized ";
    public static final String USER_NOT_FOUND = "User not found: ";

    public static final String USERNAME_NOT_FOUND = "Username not found: ";

    public static final String USER_ALREADY_EXISTS = "A User already exists for the username: ";

    public static final String ROLE_NOT_FOUND = "Role not found: ";

    public static final String EMAIL_NOT_FOUND = "Email not found: ";

    public static final String EMAIL_VERIFICATION_EXPIRED = "Verify expired: ";

    public static final String ACCOUNT_VERIFIED = "This account has already been verified.";

    public static final String WRONG_PASSWORD = "Wrong password";

    public static final String DIFFERENT_PASSWORD = "The new password is the same as the old password";

    public static final String PASSWORD_NOT_MATCHING = "Password are not the same";


    private Errors() {
        throw new UnsupportedOperationException("Not instantiable class!");
    }

}
