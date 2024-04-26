package sg.security.api.dto.role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {

    private Integer id;

    private String name;

    private String description;

}
