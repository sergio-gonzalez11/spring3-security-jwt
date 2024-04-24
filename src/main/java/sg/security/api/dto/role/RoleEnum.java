package sg.security.api.dto.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum RoleEnum {

    BASIC("basic", "User with basic roles"),
    ADMIN("admin", "User with all roles");


    @Getter
    private final String roleName;

    @Getter
    private final String roleDescription;

    public static RoleEnum findByRoleName(String roleName) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role name: " + roleName);
    }

    public static boolean hasRole(List<RoleEnum> userRoles, String roleName) {
        RoleEnum role = findByRoleName(roleName);
        return userRoles.contains(role);
    }

    public static List<String> getAllRoleNames() {
        return Arrays.stream(RoleEnum.values())
                .map(RoleEnum::getRoleName)
                .toList();
    }

    public static boolean isBasic(final List<String> roles) {
        return roles.stream().anyMatch(BASIC.roleName::equals);
    }

    public static boolean isAdmin(final List<String> roles) {
        return roles.stream().anyMatch(ADMIN.roleName::equals);
    }

}
