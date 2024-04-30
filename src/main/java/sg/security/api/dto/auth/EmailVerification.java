package sg.security.api.dto.auth;

import lombok.Builder;
import lombok.Data;
import sg.security.api.dto.user.User;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class EmailVerification implements Serializable {

    @Serial
    private static final long serialVersionUID = 1898253345716881068L;

    private Integer id;

    private String token;

    private LocalDateTime expirationTime;

    @Builder.Default
    private Boolean expired = false;

    private User user;

}
