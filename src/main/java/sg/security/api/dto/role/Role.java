package sg.security.api.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class Role {

    private Integer id;

    private String name;

    private String description;

}
