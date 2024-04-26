package sg.security.api.service;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sg.security.api.data.dto.ChangePasswordData;
import sg.security.api.data.dto.RoleData;
import sg.security.api.data.dto.UserData;
import sg.security.api.data.jpa.RoleJpaData;
import sg.security.api.data.jpa.UserJpaData;
import sg.security.api.dto.user.ChangePassword;
import sg.security.api.dto.user.User;
import sg.security.api.entity.user.UserJpa;
import sg.security.api.exception.UserNotFoundException;
import sg.security.api.mapper.UserMapper;
import sg.security.api.repository.user.UserJpaRepository;
import sg.security.api.service.user.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {


    @Mock
    UserJpaRepository repository;

    @Mock
    UserMapper mapper;
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    @InjectMocks
    UserServiceImpl service;

    UserData userData;
    UserJpaData userJpaData;

    RoleJpaData roleJpaData;

    RoleData roleData;

    ChangePasswordData changePasswordData;


    @BeforeEach
    void setUp() {
        this.userJpaData = new UserJpaData();
        this.userData = new UserData();
        this.roleJpaData = new RoleJpaData();
        this.roleData = new RoleData();
        this.changePasswordData = new ChangePasswordData();
    }


    @Nested
    class FindAll {

        @Test
        void getFindAll() {

            when(UserServiceTest.this.repository.findAllUsers()).thenReturn(userJpaData.getList());

            List<User> users = service.findAllUsers();
            assertNotNull(users);
            assertEquals(userJpaData.getList().size(), users.size());

            verify(UserServiceTest.this.repository).findAllUsers();

        }
    }

    @Nested
    class FindById {
        @Test
        void getFindById() {

            when(UserServiceTest.this.repository.findById(anyInt())).thenReturn(Optional.ofNullable(userJpaData.get(1)));
            when(UserServiceTest.this.mapper.toDTO(any(UserJpa.class))).thenReturn(userData.get(1));

            User user = service.findById(1);
            assertNotNull(user);

            verify(UserServiceTest.this.repository).findById(anyInt());
            verify(UserServiceTest.this.mapper).toDTO(any(UserJpa.class));

        }

        @Test
        void getFindByIdNotFound() {

            when(UserServiceTest.this.repository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> UserServiceTest.this.service.findById(1));

            verify(UserServiceTest.this.repository).findById(anyInt());

        }
    }

    @Nested
    class FindByEmail {

        @Test
        void getFindByEmail() {

            when(UserServiceTest.this.repository.findByEmail(anyString())).thenReturn(Optional.ofNullable(userJpaData.get(1)));
            when(UserServiceTest.this.mapper.toDTO(any(UserJpa.class))).thenReturn(userData.get(1));

            User user = service.findByEmail("Email");
            assertNotNull(user);

            verify(UserServiceTest.this.repository).findByEmail(anyString());
            verify(UserServiceTest.this.mapper).toDTO(any(UserJpa.class));

        }

        @Test
        void getFindByEmailNotFound() {

            when(UserServiceTest.this.repository.findByEmail(anyString())).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> UserServiceTest.this.service.findByEmail("Email"));

            verify(UserServiceTest.this.repository).findByEmail(anyString());

        }
    }

    @Nested
    class FindByUsername {
        @Test
        void getFindByUsername() {

            when(UserServiceTest.this.repository.findByUsername(anyString())).thenReturn(Optional.ofNullable(userJpaData.get(1)));
            when(UserServiceTest.this.mapper.toDTO(any(UserJpa.class))).thenReturn(userData.get(1));

            User user = service.findByUsername("Username");
            assertNotNull(user);

            verify(UserServiceTest.this.repository).findByUsername(anyString());
            verify(UserServiceTest.this.mapper).toDTO(any(UserJpa.class));

        }

        @Test
        void getFindByUsernameNotFound() {

            when(UserServiceTest.this.repository.findByUsername(anyString())).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> UserServiceTest.this.service.findByUsername("Username"));

            verify(UserServiceTest.this.repository).findByUsername(anyString());

        }

    }

    @Nested
    class UpdateUser {
        @Test
        void update() {

            when(UserServiceTest.this.repository.findById(anyInt())).thenReturn(Optional.ofNullable(userJpaData.get(1)));
            when(UserServiceTest.this.repository.save(any(UserJpa.class))).thenReturn(userJpaData.get(1));

            service.update(userJpaData.get(1).getId(), userData.get(1));

            verify(UserServiceTest.this.repository).findById(anyInt());
            verify(UserServiceTest.this.repository).save(any(UserJpa.class));

        }

        @Test
        void updateNotFound() {

            when(UserServiceTest.this.repository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> UserServiceTest.this.service.update(1, null));

            verify(UserServiceTest.this.repository).findById(anyInt());

        }

    }

    @Nested
    class UpdateEnabledUser {
        @Test
        void updateIsEnabled() {

            doNothing().when(UserServiceTest.this.repository).updateEnabled(anyInt());

            service.updateIsEnabled(userJpaData.get(1).getId());

            verify(UserServiceTest.this.repository).updateEnabled(anyInt());

        }

    }

    @Nested
    class ChangePasswordUser {
        @Test
        void changePassword() {

            UserJpa userJpa = userJpaData.get(1);

            ChangePassword changePassword = changePasswordData.get(1);
            changePassword.setCurrentPassword("PASSWORD_1");
            changePassword.setNewPassword("NEW_PASSWORD");
            changePassword.setConfirmationPassword("NEW_PASSWORD");

            when(UserServiceTest.this.securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            when(authentication.getPrincipal()).thenReturn(userJpa);

            when(passwordEncoder.encode(any(CharSequence.class))).thenReturn(userJpa.getPassword());
            when(passwordEncoder.matches(eq(changePassword.getCurrentPassword()), anyString())).thenReturn(Boolean.TRUE);
            when(passwordEncoder.matches(eq(changePassword.getNewPassword()), anyString())).thenReturn(Boolean.FALSE);

            doNothing().when(UserServiceTest.this.repository).updatePassword(anyInt(), anyString());

            service.changePassword(changePassword);

            verify(UserServiceTest.this.repository).updatePassword(anyInt(), anyString());

        }

        @Test
        void changePasswordEqException() {

            UserJpa userJpa = userJpaData.get(1);

            ChangePassword changePassword = changePasswordData.get(1);
            changePassword.setCurrentPassword("PASSWORD_1");
            changePassword.setNewPassword("NEW_PASSWORD");
            changePassword.setConfirmationPassword("NEW_PASSWORD");

            when(UserServiceTest.this.securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            when(authentication.getPrincipal()).thenReturn(userJpa);

            when(passwordEncoder.matches(eq(changePassword.getCurrentPassword()), anyString())).thenReturn(Boolean.FALSE);

            assertThrows(ValidationException.class, () -> UserServiceTest.this.service.changePassword(changePassword));


        }

        @Test
        void changePasswordIdenticalException() {

            UserJpa userJpa = userJpaData.get(1);

            ChangePassword changePassword = changePasswordData.get(1);
            changePassword.setCurrentPassword("PASSWORD_1");
            changePassword.setNewPassword("NEW_PASSWORD");
            changePassword.setConfirmationPassword("NEW_PASSWORD");

            when(UserServiceTest.this.securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            when(authentication.getPrincipal()).thenReturn(userJpa);

            when(passwordEncoder.matches(eq(changePassword.getCurrentPassword()), anyString())).thenReturn(Boolean.TRUE);
            when(passwordEncoder.matches(eq(changePassword.getNewPassword()), anyString())).thenReturn(Boolean.TRUE);

            assertThrows(ValidationException.class, () -> UserServiceTest.this.service.changePassword(changePassword));


        }

        @Test
        void changePassworNewException() {

            UserJpa userJpa = userJpaData.get(1);

            ChangePassword changePassword = changePasswordData.get(1);
            changePassword.setCurrentPassword("PASSWORD_1");
            changePassword.setNewPassword("NEW_PASSWORD");
            changePassword.setConfirmationPassword("NEW_PASSWORD_1");

            when(UserServiceTest.this.securityContext.getAuthentication()).thenReturn(authentication);
            SecurityContextHolder.setContext(securityContext);

            when(authentication.getPrincipal()).thenReturn(userJpa);

            when(passwordEncoder.matches(eq(changePassword.getCurrentPassword()), anyString())).thenReturn(Boolean.TRUE);
            when(passwordEncoder.matches(eq(changePassword.getNewPassword()), anyString())).thenReturn(Boolean.FALSE);

            assertThrows(ValidationException.class, () -> UserServiceTest.this.service.changePassword(changePassword));


        }

    }

    @Nested
    class Delete {

        @Test
        void deleteById() {

            doNothing().when(UserServiceTest.this.repository).deleteById(anyInt());

            service.delete(1);

            verify(UserServiceTest.this.repository).deleteById(anyInt());

        }

        @Test
        void deleteByUsername() {

            when(UserServiceTest.this.repository.findByUsername(anyString())).thenReturn(Optional.ofNullable(userJpaData.get(1)));
            when(UserServiceTest.this.mapper.toDTO(any(UserJpa.class))).thenReturn(userData.get(1));
            doNothing().when(UserServiceTest.this.repository).deleteById(anyInt());

            service.delete("USERNAME_1");

            verify(UserServiceTest.this.repository).findByUsername(anyString());
            verify(UserServiceTest.this.mapper).toDTO(any(UserJpa.class));
            verify(UserServiceTest.this.repository).deleteById(anyInt());

        }

        @Test
        void deleteByUsernameException() {

            when(UserServiceTest.this.repository.findByUsername(anyString())).thenReturn(Optional.empty());

            assertThrows(UserNotFoundException.class, () -> UserServiceTest.this.service.delete("Username"));

        }
    }


}
