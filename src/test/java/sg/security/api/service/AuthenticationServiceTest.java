package sg.security.api.service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import sg.security.api.config.jwt.JwtUtils;
import sg.security.api.config.util.UrlHelper;
import sg.security.api.data.dto.LoginRequestData;
import sg.security.api.data.dto.RegisterRequestData;
import sg.security.api.data.dto.UserData;
import sg.security.api.data.jpa.EmailVerificationJpaData;
import sg.security.api.data.jpa.RoleJpaData;
import sg.security.api.data.jpa.UserJpaData;
import sg.security.api.dto.auth.LoginRequest;
import sg.security.api.dto.auth.LoginResponse;
import sg.security.api.dto.auth.RegisterRequest;
import sg.security.api.dto.user.User;
import sg.security.api.entity.email.EmailVerificationJpa;
import sg.security.api.entity.role.RoleJpa;
import sg.security.api.entity.user.UserJpa;
import sg.security.api.event.EmailEvent;
import sg.security.api.exception.UserNotFoundException;
import sg.security.api.mapper.UserMapper;
import sg.security.api.repository.email.EmailVerificationJpaRepository;
import sg.security.api.repository.role.RoleJpaRepository;
import sg.security.api.repository.user.UserJpaRepository;
import sg.security.api.service.auth.AuthServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    RoleJpaRepository roleJpaRepository;

    @Mock
    EmailVerificationJpaRepository emailVerificationJpaRepository;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    ApplicationEventPublisher publisher;

    @Mock
    UrlHelper urlHelper;
    @Mock
    UserMapper userMapper;

    @Mock
    HttpServletRequest request;

    @Mock
    UserJpaRepository userJpaRepository;

    @InjectMocks
    AuthServiceImpl authService;

    UserJpaData userJpaData;

    RoleJpaData roleJpaData;

    RegisterRequestData registerRequestData;

    UserData userData;

    LoginRequestData loginRequestData;

    EmailVerificationJpaData emailVerificationJpaData;

    @BeforeEach
    void setUp() {
        this.userJpaData = new UserJpaData();
        this.roleJpaData = new RoleJpaData();
        this.registerRequestData = new RegisterRequestData();
        this.userData = new UserData();
        this.loginRequestData = new LoginRequestData();
        this.emailVerificationJpaData = new EmailVerificationJpaData();
    }


    @Nested
    class Login {

        @Test
        void loginIsOk() {

            LoginRequest loginRequest = loginRequestData.get(1);
            UserJpa userJpa = userJpaData.get(1);

            Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            when(AuthenticationServiceTest.this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
            when(AuthenticationServiceTest.this.userJpaRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(userJpa));

            LoginResponse response = authService.login(loginRequest);
            assertNotNull(response);

            verify(AuthenticationServiceTest.this.authenticationManager).authenticate(any(Authentication.class));
            verify(AuthenticationServiceTest.this.userJpaRepository).findByUsername(anyString());

        }

        @Test
        void loginNotFoundException() {

            LoginRequest loginRequest = loginRequestData.get(1);

            when(AuthenticationServiceTest.this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

            assertThrows(UserNotFoundException.class, () -> AuthenticationServiceTest.this.authService.login(loginRequest));

            verify(AuthenticationServiceTest.this.authenticationManager).authenticate(any(Authentication.class));

        }
    }

    @Nested
    class Register {

        @Test
        void registerIsOk() throws Exception {

            RegisterRequest registerRequest = registerRequestData.get(1);
            User user = userData.get(1);

            RoleJpa roleJpa = roleJpaData.get(1);
            UserJpa userJpa = userJpaData.get(1);
            userJpa.setRole(roleJpa);


            when(AuthenticationServiceTest.this.userJpaRepository.findByUsernameAndEmail(anyString(), anyString())).thenReturn(Optional.empty());
            when(AuthenticationServiceTest.this.roleJpaRepository.findByName(anyString())).thenReturn(Optional.of(roleJpa));
            when(AuthenticationServiceTest.this.userMapper.toRegisterJpa(any(RegisterRequest.class))).thenReturn(userJpa);
            when(AuthenticationServiceTest.this.userJpaRepository.save(any())).thenReturn(userJpa);
            when(AuthenticationServiceTest.this.userMapper.toDTO(any(UserJpa.class))).thenReturn(user);
            doCallRealMethod().when(AuthenticationServiceTest.this.publisher).publishEvent(any(EmailEvent.class));


            AuthenticationServiceTest.this.authService.register(registerRequest, request);


            verify(AuthenticationServiceTest.this.userJpaRepository).findByUsernameAndEmail(anyString(), anyString());
            verify(AuthenticationServiceTest.this.roleJpaRepository).findByName(anyString());
            verify(AuthenticationServiceTest.this.userMapper).toRegisterJpa(any());
            verify(AuthenticationServiceTest.this.userJpaRepository).save(any());
            verify(AuthenticationServiceTest.this.publisher).publishEvent(any(EmailEvent.class));

        }

        @Test
        void registerUserAlreadyExistsException() {

            RegisterRequest registerRequest = registerRequestData.get(1);

            RoleJpa roleJpa = roleJpaData.get(1);
            UserJpa userJpa = userJpaData.get(1);
            userJpa.setRole(roleJpa);


            when(AuthenticationServiceTest.this.userJpaRepository.findByUsernameAndEmail(anyString(), anyString())).thenReturn(Optional.of(userJpa));
            when(AuthenticationServiceTest.this.roleJpaRepository.findByName(anyString())).thenReturn(Optional.of(roleJpa));


            assertThrows(Exception.class, () -> AuthenticationServiceTest.this.authService.register(registerRequest, request));


            verify(AuthenticationServiceTest.this.userJpaRepository).findByUsernameAndEmail(anyString(), anyString());
            verify(AuthenticationServiceTest.this.roleJpaRepository).findByName(anyString());

        }
    }

    @Nested
    class EmailConfirmation {

        @Test
        void sendEmailConfirmationNotExpiratedIsOk() throws Exception {

            int EXPIRATION_TIME = 15;

            UserJpa userJpa = userJpaData.get(1);
            userJpa.setIsEnabled(Boolean.FALSE);

            EmailVerificationJpa emailVerificationJpa = emailVerificationJpaData.get(1);
            emailVerificationJpa.setExpirationTime(LocalDateTime.now().plusMinutes(EXPIRATION_TIME));
            emailVerificationJpa.setUser(userJpa);

            when(AuthenticationServiceTest.this.emailVerificationJpaRepository.findByToken(anyString())).thenReturn(Optional.of(emailVerificationJpa));
            doNothing().when(AuthenticationServiceTest.this.userJpaRepository).updateEnabled(anyInt());


            AuthenticationServiceTest.this.authService.sendEmailConfirmation(emailVerificationJpa.getToken());


            verify(AuthenticationServiceTest.this.emailVerificationJpaRepository).findByToken(anyString());
            verify(AuthenticationServiceTest.this.userJpaRepository).updateEnabled(anyInt());

        }

        @Test
        void sendEmailConfirmationExpiratedIsOk() throws Exception {

            UserJpa userJpa = userJpaData.get(1);
            userJpa.setIsEnabled(Boolean.FALSE);

            EmailVerificationJpa emailVerificationJpa = emailVerificationJpaData.get(1);
            emailVerificationJpa.setUser(userJpa);
            emailVerificationJpa.setExpirationTime(LocalDateTime.now());

            when(AuthenticationServiceTest.this.emailVerificationJpaRepository.findByToken(anyString())).thenReturn(Optional.of(emailVerificationJpa));
            doNothing().when(AuthenticationServiceTest.this.emailVerificationJpaRepository).deleteById(anyInt());


            AuthenticationServiceTest.this.authService.sendEmailConfirmation(emailVerificationJpa.getToken());


            verify(AuthenticationServiceTest.this.emailVerificationJpaRepository).findByToken(anyString());

        }

        @Test
        void sendEmailConfirmationExpiratedIsOldVerificated() {

            UserJpa userJpa = userJpaData.get(1);
            userJpa.setIsEnabled(Boolean.TRUE);

            EmailVerificationJpa emailVerificationJpa = emailVerificationJpaData.get(1);
            emailVerificationJpa.setUser(userJpa);

            when(AuthenticationServiceTest.this.emailVerificationJpaRepository.findByToken(anyString())).thenReturn(Optional.of(emailVerificationJpa));


            assertThrows(Exception.class, () -> AuthenticationServiceTest.this.authService.sendEmailConfirmation(emailVerificationJpa.getToken()));


            verify(AuthenticationServiceTest.this.emailVerificationJpaRepository).findByToken(anyString());

        }
    }
}
