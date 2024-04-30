package sg.security.api.dto.role;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sg.security.api.dto.email.TypeEmailEnum;
import sg.security.api.dto.role.RoleEnum;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RolelEnumTest {

    @Nested
    class FindAllRoles {

        @Test
        void allRoles() {
            assertNotNull(RoleEnum.getAllRoleNames());
        }

        @Test
        void emptyRoles() {

            List<String> roles = new java.util.ArrayList<>(List.of(RoleEnum.getAllRoleNames().toString()));
            roles.clear();

            assertEquals(Collections.emptyList(), roles);
        }
    }

    @Nested
    class FindRole {

        @Test
        void findRoleIsOk() {
            assertEquals(RoleEnum.BASIC,
                    RoleEnum.findByRoleName(RoleEnum.BASIC.getRoleName()));
        }

        @Test
        void findRoleIsException() {
            assertThrows(IllegalArgumentException.class, () -> RoleEnum.findByRoleName("invalid_code"));
        }
    }

    @Nested
    class IsBasic {

        @Test
        void isBasicTrue() {
            assertEquals(Boolean.TRUE, RoleEnum.isBasic(List.of("basic")));
        }

        @Test
        void isBasicFalse() {
            assertEquals(Boolean.FALSE, RoleEnum.isBasic(List.of("admin")));
        }
    }

    @Nested
    class IsAdmin {

        @Test
        void isAdminTrue() {
            assertEquals(Boolean.TRUE, RoleEnum.isAdmin(List.of("admin")));
        }

        @Test
        void isAdminFalse() {
            assertEquals(Boolean.FALSE, RoleEnum.isAdmin(List.of("basic")));
        }
    }

}
