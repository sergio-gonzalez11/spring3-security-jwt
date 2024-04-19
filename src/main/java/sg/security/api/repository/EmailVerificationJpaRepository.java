package sg.security.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.security.api.entity.emailVerification.EmailVerificationJpa;

import java.util.Optional;

public interface EmailVerificationJpaRepository extends JpaRepository<EmailVerificationJpa, Integer> {

    Optional<EmailVerificationJpa> findByToken(String token);

    void deleteByUserId(Integer userId);

}