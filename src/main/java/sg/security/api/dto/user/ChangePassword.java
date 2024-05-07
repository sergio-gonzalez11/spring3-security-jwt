package sg.security.api.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class ChangePassword implements Serializable {

    @Serial
    private static final long serialVersionUID = 1815809659079468450L;

    @NotNull
    private String currentPassword;

    @NotNull
    private String newPassword;

    @NotNull
    private String confirmationPassword;

}
