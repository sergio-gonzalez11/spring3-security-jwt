package sg.security.api.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class LoginResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -874646804013467597L;

    private String token;

    private int expiresIn;

}
