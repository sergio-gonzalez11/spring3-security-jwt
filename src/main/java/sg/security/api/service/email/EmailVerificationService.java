package sg.security.api.service.email;

import sg.security.api.dto.auth.EmailVerification;
import sg.security.api.dto.user.User;

public interface EmailVerificationService {

    EmailVerification getEmailVerificationByToken(String token);

    void saveEmailVerification(EmailVerification emailVerification);

    void delete(Integer id);

    void deleteByUser(Integer userId);

}
