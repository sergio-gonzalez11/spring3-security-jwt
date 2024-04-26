package sg.security.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import sg.security.api.dto.auth.LoginRequest;
import sg.security.api.dto.auth.LoginResponse;
import sg.security.api.dto.auth.RegisterRequest;
import sg.security.api.service.auth.AuthService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auths")
public class AuthController {

    private final @NonNull AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("[AuthController] Start Login: {}", loginRequest);
        return ResponseEntity.ok(authService.login(loginRequest));
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request) throws Exception {
        log.info("[AuthController] Start Register: {}", registerRequest);
        authService.register(registerRequest, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/verification")
    public ResponseEntity<Void> verifyEmail(@Valid @RequestParam("token") String token) throws Exception {
        log.info("[AuthController] Start verify email: {}", token);
        authService.sendEmailConfirmation(token);
        return ResponseEntity.noContent().build();
    }


}
