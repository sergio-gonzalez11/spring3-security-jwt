package sg.security.api.dto.user;

import lombok.Builder;
import lombok.Data;
import sg.security.api.dto.role.Role;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -8290026120346257602L;

    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String username;

    private String password;

    private LocalDate birthdate;

    private Role role;

    @Builder.Default
    private Boolean isEnabled = false;

}
