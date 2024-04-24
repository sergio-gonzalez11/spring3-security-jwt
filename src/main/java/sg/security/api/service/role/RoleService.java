package sg.security.api.service.role;

import sg.security.api.dto.role.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    List<Role> findByIds(List<Integer> ids);

    Role findByRoleId(Integer idRole);

    Role findByRoleName(String roleName);

    void save(Role role);

    void update(Role role) throws Exception;

    void delete(Integer roleId);

}
