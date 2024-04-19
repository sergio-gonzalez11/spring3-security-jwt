package sg.security.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
