package sg.security.api.dto.auth;

import lombok.Builder;
import lombok.Data;
import sg.security.api.dto.user.User;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class RegisterResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -4486624033591678982L;

    private User user;

}
