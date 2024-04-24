package sg.security.api.constant;

public final class Constants {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String X_AUTH_TOKEN = "X-Auth-Token";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_CREDENTIAL = "Access-Control-Allow-Credential";
    public static final String USER_APPLICATION = "api-security";


    private Constants() {
        throw new UnsupportedOperationException("Not instantiable class!");
    }

}
