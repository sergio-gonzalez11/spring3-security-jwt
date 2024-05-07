package sg.security.api.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sg.security.api.data.dto.UserData;
import sg.security.api.data.jpa.UserJpaData;
import sg.security.api.repository.user.UserJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {


    @Mock
    UserJpaRepository repository;

    @InjectMocks
    UserDetailsServiceImpl service;

    UserData userData;
    UserJpaData userJpaData;


    @BeforeEach
    void setUp() {
        this.userJpaData = new UserJpaData();
        this.userData = new UserData();
    }


    @Nested
    class LoadUserByUsername {
        @Test
        void loadUserByUsername() {

            when(UserDetailsServiceTest.this.repository.findByUsername(anyString())).thenReturn(Optional.ofNullable(userJpaData.get(1)));

            UserDetails userDetails = service.loadUserByUsername("USERNAME_1");
            assertNotNull(userDetails);

            verify(UserDetailsServiceTest.this.repository).findByUsername(anyString());

        }

        @Test
        void loadUserByUsernameNotFound() {

            when(UserDetailsServiceTest.this.repository.findByUsername(anyString())).thenReturn(Optional.empty());

            assertThrows(UsernameNotFoundException.class, () -> UserDetailsServiceTest.this.service.loadUserByUsername("USERNAME_1"));

            verify(UserDetailsServiceTest.this.repository).findByUsername(anyString());

        }
    }

}
