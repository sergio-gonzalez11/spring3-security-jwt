package sg.security.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import sg.security.api.dto.LoginRequest;
import sg.security.api.dto.LoginResponse;
import sg.security.api.dto.RegisterRequest;
import sg.security.api.service.auth.AuthService;

@RestController
@AllArgsConstructor
@RequestMapping("/auths")
public class AuthController {

    private final @NonNull AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }


    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request) throws Exception {
        authService.register(registerRequest, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/verification")
    public ResponseEntity<Void> verifyEmail(@Valid @RequestParam("token") String token) throws Exception {
        authService.sendEmailConfirmation(token);
        return ResponseEntity.noContent().build();
    }


}
