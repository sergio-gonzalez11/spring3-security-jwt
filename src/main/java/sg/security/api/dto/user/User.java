package sg.security.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.security.api.dto.role.Role;

import java.time.LocalDate;

@Data
@Builder
public class User {

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
