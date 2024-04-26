package sg.security.api.dto.auth;

import lombok.Builder;
import lombok.Data;
import sg.security.api.dto.user.User;

import java.time.LocalDateTime;

@Data
@Builder
public class EmailVerification {

    private Integer id;

    private String token;

    private LocalDateTime expirationTime;

    @Builder.Default
    private Boolean expired = false;

    private User user;

}
