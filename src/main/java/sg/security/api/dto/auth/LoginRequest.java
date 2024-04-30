package sg.security.api.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class LoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2782480810388268212L;

    @NotNull
    private String username;

    @NotNull
    private String password;

}
