package sg.security.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerification {

    private Integer id;

    private String token;

    private Date expirationTime;

    @Builder.Default
    private Boolean revoked = false;

    @Builder.Default
    private Boolean expired = false;

    private User user;

}
