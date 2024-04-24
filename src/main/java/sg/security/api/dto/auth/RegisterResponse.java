package sg.security.api.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sg.security.api.dto.user.User;

@Data
@Builder
public class RegisterResponse {

    private User user;

}
