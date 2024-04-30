package sg.security.api.dto.role;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class CreateRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 3573371650983825137L;

    @NotNull
    private String name;

    @NotNull
    private String description;
}
