package sg.security.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sg.security.api.dto.RegisterResponse;
import sg.security.api.entity.user.UserJpa;

import java.util.List;

@Mapper(uses = {
        UserMapper.class,
        RoleMapper.class,
        DateMapper.class
})
public interface AuthMapper {

    @Mapping(target = "user", source = "entity")
    RegisterResponse toDTO(UserJpa entity);

    List<RegisterResponse> toDTOs(List<UserJpa> entities);


}
