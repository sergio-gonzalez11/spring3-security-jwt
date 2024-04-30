package sg.security.api.service.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sg.security.api.data.dto.EmailVerificationData;
import sg.security.api.data.dto.UserData;
import sg.security.api.data.jpa.EmailVerificationJpaData;
import sg.security.api.data.jpa.UserJpaData;
import sg.security.api.dto.auth.EmailVerification;
import sg.security.api.entity.email.EmailVerificationJpa;
import sg.security.api.entity.user.UserJpa;
import sg.security.api.exception.EmailVerificationNotFoundException;
import sg.security.api.mapper.EmailVerificationMapper;
import sg.security.api.repository.email.EmailVerificationJpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {


    @Mock
    EmailVerificationJpaRepository repository;

    @Mock
    EmailVerificationMapper mapper;

    @InjectMocks
    EmailVerificationServiceImpl service;

    EmailVerificationJpaData emailVerificationJpaData;

    UserData userData;
    UserJpaData userJpaData;

    EmailVerificationData emailVerificationData;


    @BeforeEach
    void setUp() {
        this.emailVerificationJpaData = new EmailVerificationJpaData();
        this.userData = new UserData();
        this.userJpaData = new UserJpaData();
        this.emailVerificationData = new EmailVerificationData();
    }


    @Nested
    class EmailVerificationByToken {

        @Test
        void getEmailVerificationByToken() {

            UserJpa userJpa = userJpaData.get(1);

            EmailVerificationJpa emailVerificationJpa = emailVerificationJpaData.get(1);
            emailVerificationJpa.setUser(userJpa);

            when(EmailVerificationServiceTest.this.repository.findByToken(anyString())).thenReturn(Optional.of(emailVerificationJpa));
            when(EmailVerificationServiceTest.this.mapper.toDTO(any(EmailVerificationJpa.class))).thenReturn(emailVerificationData.get(1));

            EmailVerification emailVerification = service.getEmailVerificationByToken(emailVerificationData.get(1).getToken());
            assertNotNull(emailVerification);

            verify(EmailVerificationServiceTest.this.repository).findByToken(anyString());
            verify(EmailVerificationServiceTest.this.mapper).toDTO(any(EmailVerificationJpa.class));

        }

        @Test
        void getEmailVerificationByTokenNotFoundException() {

            when(EmailVerificationServiceTest.this.repository.findByToken(anyString())).thenReturn(Optional.empty());

            assertThrows(EmailVerificationNotFoundException.class, () -> EmailVerificationServiceTest.this.service.getEmailVerificationByToken(emailVerificationData.get(1).getToken()));

            verify(EmailVerificationServiceTest.this.repository).findByToken(anyString());


        }

    }

    @Nested
    class CreateEmailVerification {

        @Test
        void save() {

            UserJpa userJpa = userJpaData.get(1);

            EmailVerificationJpa emailVerificationJpa = emailVerificationJpaData.get(1);
            emailVerificationJpa.setUser(userJpa);

            when(EmailVerificationServiceTest.this.mapper.toJPA(any(EmailVerification.class))).thenReturn(emailVerificationJpaData.get(1));
            when(EmailVerificationServiceTest.this.repository.save(any(EmailVerificationJpa.class))).thenReturn(emailVerificationJpa);

            service.saveEmailVerification(emailVerificationData.get(1).getUser(), emailVerificationData.get(1).getToken());

            verify(EmailVerificationServiceTest.this.mapper).toJPA(any(EmailVerification.class));
            verify(EmailVerificationServiceTest.this.repository).save(any(EmailVerificationJpa.class));
        }

        @Test
        void saveObject() {

            UserJpa userJpa = userJpaData.get(1);

            EmailVerificationJpa emailVerificationJpa = emailVerificationJpaData.get(1);
            emailVerificationJpa.setUser(userJpa);

            when(EmailVerificationServiceTest.this.mapper.toJPA(any(EmailVerification.class))).thenReturn(emailVerificationJpaData.get(1));
            when(EmailVerificationServiceTest.this.repository.save(any(EmailVerificationJpa.class))).thenReturn(emailVerificationJpa);

            service.saveEmailVerification(emailVerificationData.get(1));

            verify(EmailVerificationServiceTest.this.mapper).toJPA(any(EmailVerification.class));
            verify(EmailVerificationServiceTest.this.repository).save(any(EmailVerificationJpa.class));

        }

    }

    @Nested
    class DeleteEmailVerification {

        @Test
        void delete() {

            doNothing().when(EmailVerificationServiceTest.this.repository).deleteById(anyInt());

            service.delete(emailVerificationData.get(1).getId());

            verify(EmailVerificationServiceTest.this.repository).deleteById(anyInt());
        }

        @Test
        void deleteByUserId() {

            UserJpa userJpa = userJpaData.get(1);

            EmailVerificationJpa emailVerificationJpa = emailVerificationJpaData.get(1);
            emailVerificationJpa.setUser(userJpa);

            doNothing().when(EmailVerificationServiceTest.this.repository).deleteByUserId(anyInt());

            service.deleteByUser(emailVerificationJpa.getUser().getId());

            verify(EmailVerificationServiceTest.this.repository).deleteByUserId(anyInt());

        }

    }


}
