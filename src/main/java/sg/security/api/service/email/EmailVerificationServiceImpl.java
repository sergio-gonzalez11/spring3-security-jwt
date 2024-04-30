package sg.security.api.service.email;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.security.api.dto.auth.EmailVerification;
import sg.security.api.dto.user.User;
import sg.security.api.exception.EmailVerificationNotFoundException;
import sg.security.api.mapper.EmailVerificationMapper;
import sg.security.api.repository.email.EmailVerificationJpaRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final @NonNull EmailVerificationJpaRepository repository;
    private final @NonNull EmailVerificationMapper mapper;
    private static final int EXPIRATION_TIME = 15;


    @Override
    public EmailVerification getEmailVerificationByToken(String token) {
        return repository.findByToken(token)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EmailVerificationNotFoundException(token));
    }

    @Override
    @Transactional
    public void saveEmailVerification(User user, String token) {

        EmailVerification emailVerification = EmailVerification.builder()
                .token(token)
                .expirationTime(getTokenExpirationTime())
                .user(user)
                .build();

        repository.save(mapper.toJPA(emailVerification));
    }

    @Override
    @Transactional
    public void saveEmailVerification(EmailVerification emailVerification) {
        emailVerification.setExpirationTime(getTokenExpirationTime());
        repository.save(mapper.toJPA(emailVerification));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByUser(Integer userId) {
        repository.deleteByUserId(userId);
    }

    public LocalDateTime getTokenExpirationTime() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
    }
}
