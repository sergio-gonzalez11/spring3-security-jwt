package sg.security.api.mapper;

import org.mapstruct.Mapper;
import sg.security.api.dto.role.CreateRole;
import sg.security.api.dto.role.Role;
import sg.security.api.entity.role.RoleJpa;

import java.util.List;

@Mapper
public interface RoleMapper {

    Role toDTO(RoleJpa entity);

    List<Role> toDTOs(List<RoleJpa> entities);

    RoleJpa toJPA(Role entity);

    List<RoleJpa> toJPAs(List<Role> entities);

    Role toDTO(CreateRole entity);


}
