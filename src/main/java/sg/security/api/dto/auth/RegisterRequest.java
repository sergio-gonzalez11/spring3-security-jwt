package sg.security.api.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import sg.security.api.validator.email.Email;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class RegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 8054176119085863966L;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private LocalDate birthdate;

}
