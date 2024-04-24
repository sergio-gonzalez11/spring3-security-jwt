package sg.security.api.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ChangePassword {

    @NotNull
    private String currentPassword;

    @NotNull
    private String newPassword;

    @NotNull
    private String confirmationPassword;

}
