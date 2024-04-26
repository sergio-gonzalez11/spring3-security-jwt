package sg.security.api.dto.auth;

import lombok.Builder;
import lombok.Data;
import sg.security.api.dto.user.User;

@Data
@Builder
public class RegisterResponse {

    private User user;

}
