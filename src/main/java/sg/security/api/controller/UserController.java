package sg.security.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final @NonNull UserService userService;
    private final @NonNull EmailVerificationService emailVerificationService;

    @AuthorizeAccess
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }


    @AuthorizeAccess
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }


    @AuthorizeAccess
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }


    @AuthorizeAccess
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }


    @AuthorizeAccess
    @PutMapping("/{userId}")
    public ResponseEntity<Void> update(@PathVariable("userId") Integer userId, @Valid @RequestBody User user) {
        userService.update(userId, user);
        return ResponseEntity.noContent().build();
    }

    @AuthorizeAccess
    @PutMapping("/change/password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePassword changePassword) {
        userService.changePassword(changePassword);
        return ResponseEntity.noContent().build();
    }


    @AdminAccess
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Integer userId) {
        emailVerificationService.deleteByUser(userId);
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
