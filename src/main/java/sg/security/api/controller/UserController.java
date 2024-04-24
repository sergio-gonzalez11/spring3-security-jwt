package sg.security.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import sg.security.api.dto.user.ChangePassword;
import sg.security.api.dto.user.User;
import sg.security.api.service.email.EmailVerificationService;
import sg.security.api.service.user.UserService;
import sg.security.api.shared.AdminAccess;
import sg.security.api.shared.AuthorizeAccess;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final @NonNull UserService userService;
    private final @NonNull EmailVerificationService emailVerificationService;

    @AuthorizeAccess
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("[UserController] Start getAllUsers");
        return ResponseEntity.ok(userService.findAllUsers());
    }


    @AuthorizeAccess
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@Valid @PathVariable("userId") Integer userId) {
        log.info("[UserController] Start getUserById: {}", userId);
        return ResponseEntity.ok(userService.findById(userId));
    }


    @AuthorizeAccess
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@Valid @PathVariable("username") String username) {
        log.info("[UserController] Start getUserByUsername: {}", username);
        return ResponseEntity.ok(userService.findByUsername(username));
    }


    @AuthorizeAccess
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@Valid @PathVariable("email") String email) {
        log.info("[UserController] Start getUserByEmail: {}", email);
        return ResponseEntity.ok(userService.findByEmail(email));
    }


    @AuthorizeAccess
    @PutMapping("/{userId}")
    public ResponseEntity<Void> update(@Valid @PathVariable("userId") Integer userId, @Valid @RequestBody User user) {
        log.info("[UserController] Start update: {}", user);
        userService.update(userId, user);
        return ResponseEntity.noContent().build();
    }

    @AuthorizeAccess
    @PutMapping("/change/password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePassword changePassword) {
        log.info("[UserController] Start changePassword: {}", changePassword);
        userService.changePassword(changePassword);
        return ResponseEntity.noContent().build();
    }


    @AdminAccess
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@Valid @PathVariable("userId") Integer userId) {
        log.info("[UserController] Start delete: {}", userId);
        emailVerificationService.deleteByUser(userId);
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
