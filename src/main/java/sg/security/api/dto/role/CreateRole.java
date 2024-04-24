package sg.security.api.dto.role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class CreateRole {

    @NotNull
    private String name;

    @NotNull
    private String description;
}
