package sg.security.api.dto.role;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = -5223865370654187239L;

    private Integer id;

    private String name;

    private String description;

}
