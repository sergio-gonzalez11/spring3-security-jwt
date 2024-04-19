package sg.security.api.entity.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public enum RoleEnum {

    BASIC("accessBasic", "User with basic roles"),
    USER("accessUser", "User with default roles"),
    ADMIN("accessAdmin", "User with all roles");


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

    public static boolean isUser(final List<String> roles) {
        return roles.stream().anyMatch(USER.roleName::equals);
    }

    public static boolean isAdmin(final List<String> roles) {
        return roles.stream().anyMatch(ADMIN.roleName::equals);
    }

}
