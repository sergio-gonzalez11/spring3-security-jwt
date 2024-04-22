package sg.security.api.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.security.api.config.jwt.JwtUtils;
import sg.security.api.config.util.UrlHelper;
import sg.security.api.constant.Errors;
import sg.security.api.dto.LoginRequest;
import sg.security.api.dto.LoginResponse;
import sg.security.api.dto.RegisterRequest;
import sg.security.api.entity.emailVerification.EmailVerificationJpa;
import sg.security.api.entity.role.RoleEnum;
import sg.security.api.entity.role.RoleJpa;
import sg.security.api.entity.user.UserJpa;
import sg.security.api.event.EmailEvent;
import sg.security.api.exception.EmailVerificationNotFoundException;
import sg.security.api.exception.RoleNotFoundException;
import sg.security.api.exception.UserAlreadyExistsException;
import sg.security.api.exception.UserNotFoundException;
import sg.security.api.mapper.UserMapper;
import sg.security.api.repository.EmailVerificationJpaRepository;
import sg.security.api.repository.RoleJpaRepository;
import sg.security.api.repository.UserJpaRepository;

import java.util.Calendar;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final @NonNull AuthenticationManager authenticationManager;
    private final @NonNull UserJpaRepository userJpaRepository;
    private final @NonNull RoleJpaRepository roleJpaRepository;
    private final @NonNull EmailVerificationJpaRepository emailVerificationJpaRepository;
    private final @NonNull JwtUtils jwtUtils;
    private final @NonNull PasswordEncoder passwordEncoder;
    private final @NonNull ApplicationEventPublisher publisher;
    private final @NonNull UrlHelper urlHelper;
    private final @NonNull UserMapper userMapper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        var userJpa = userJpaRepository
                .findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UserNotFoundException(loginRequest.getUsername()));

        String jwtToken = jwtUtils.generateToken(userJpa);

        return LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtUtils.getExpirationTime())
                .build();
    }

    @Override
    @Transactional
    public void register(RegisterRequest registerRequest, HttpServletRequest request) throws Exception {

        var userJpa = userJpaRepository
                .findByUsernameAndEmail(registerRequest.getUsername(), registerRequest.getEmail()).orElse(null);

        var roleJpa = roleJpaRepository
                .findByName(RoleEnum.BASIC.getRoleName()).orElseThrow(RoleNotFoundException::new);

        if (Objects.nonNull(userJpa)) {
            throw new UserAlreadyExistsException(userJpa.getUsername());
        }

        UserJpa create = saveUserJpa(registerRequest, roleJpa);
        publisher.publishEvent(new EmailEvent(userMapper.toDTO(create), urlHelper.getApplicationUrl(request)));

    }

    private UserJpa saveUserJpa(RegisterRequest registerRequest, RoleJpa roleJpa) {
        UserJpa create = userMapper.toRegisterJpa(registerRequest);
        create.setPassword(passwordEncoder.encode(create.getPassword()));
        create.setRole(roleJpa);
        return userJpaRepository.save(create);
    }

    @Override
    @Transactional
    public void sendEmailConfirmation(String token) throws Exception {

        var emailVerificationJpa = emailVerificationJpaRepository
                .findByToken(token).orElseThrow(() -> new EmailVerificationNotFoundException(token));

        if (Boolean.TRUE.equals(emailVerificationJpa.getUser().getIsEnabled())) {
            throw new Exception(Errors.ACCOUNT_VERIFIED);
        }

        this.isValidVerification(emailVerificationJpa);
    }

    private void isValidVerification(EmailVerificationJpa emailVerificationJpa) {

        var userJpa = emailVerificationJpa.getUser();

        Calendar calendar = Calendar.getInstance();

        if ((emailVerificationJpa.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            emailVerificationJpaRepository.deleteById(emailVerificationJpa.getId());

        } else {

            userJpaRepository.updateEnabled(userJpa.getId());
        }
    }

}
