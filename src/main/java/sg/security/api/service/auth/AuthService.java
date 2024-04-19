package sg.security.api.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import sg.security.api.dto.LoginRequest;
import sg.security.api.dto.LoginResponse;
import sg.security.api.dto.RegisterRequest;

public interface AuthService {

    LoginResponse login(LoginRequest loginReques);

    void register(RegisterRequest registerRequest, HttpServletRequest request) throws Exception;

    void sendEmailConfirmation(String token) throws Exception;

}
