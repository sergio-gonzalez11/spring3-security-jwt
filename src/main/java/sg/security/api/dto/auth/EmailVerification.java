package sg.security.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.security.api.dto.user.User;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class EmailVerification {

    private Integer id;

    private String token;

    private LocalDateTime expirationTime;

    @Builder.Default
    private Boolean revoked = false;

    @Builder.Default
    private Boolean expired = false;

    private User user;

}
