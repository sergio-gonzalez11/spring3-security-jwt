package sg.security.api.dto.role;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRole {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
