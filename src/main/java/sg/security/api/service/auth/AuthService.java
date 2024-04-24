package sg.security.api.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import sg.security.api.dto.auth.LoginRequest;
import sg.security.api.dto.auth.LoginResponse;
import sg.security.api.dto.auth.RegisterRequest;

public interface AuthService {

    LoginResponse login(LoginRequest loginReques);

    void register(RegisterRequest registerRequest, HttpServletRequest request) throws Exception;

    void sendEmailConfirmation(String token) throws Exception;

}
