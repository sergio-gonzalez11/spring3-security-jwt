package sg.security.api.shared;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole("
        + "(T(sg.security.api.entity.role.RoleEnum).USER.getRoleName().toUpperCase()),"
        + "(T(sg.security.api.entity.role.RoleEnum).ADMIN.getRoleName().toUpperCase()))")
public @interface AuthorizeAccess {

}
