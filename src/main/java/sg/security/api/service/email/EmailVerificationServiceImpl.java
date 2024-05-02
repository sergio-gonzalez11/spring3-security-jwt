package sg.security.api.service.email;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.security.api.dto.auth.EmailVerification;
import sg.security.api.exception.EmailVerificationNotFoundException;
import sg.security.api.mapper.EmailVerificationMapper;
import sg.security.api.repository.email.EmailVerificationJpaRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final @NonNull EmailVerificationJpaRepository repository;
    private final @NonNull EmailVerificationMapper mapper;


    @Override
    public EmailVerification getEmailVerificationByToken(String token) {
        return repository.findByToken(token)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EmailVerificationNotFoundException(token));
    }

    @Override
    @Transactional
    public void saveEmailVerification(EmailVerification emailVerification) {
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

}
