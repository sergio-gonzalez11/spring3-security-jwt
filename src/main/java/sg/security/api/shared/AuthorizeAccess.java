package sg.security.api.shared;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole("
        + "(T(sg.security.api.dto.role.RoleEnum).BASIC.getRoleName()),"
        + "(T(sg.security.api.dto.role.RoleEnum).ADMIN.getRoleName()))")
public @interface AuthorizeAccess {
}
