package sg.security.api.service.email;

import sg.security.api.dto.EmailVerification;
import sg.security.api.dto.User;

public interface EmailVerificationService {

    EmailVerification getEmailVerificationByToken(String token);

    void saveEmailVerification(User user, String token);

    void saveEmailVerification(EmailVerification emailVerification);

    void delete(Integer id);

    void deleteByUser(Integer userId);

}
