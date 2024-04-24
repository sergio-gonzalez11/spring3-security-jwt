package sg.security.api.mapper;

import org.mapstruct.Mapper;
import sg.security.api.dto.auth.RegisterRequest;
import sg.security.api.dto.user.User;
import sg.security.api.entity.user.UserJpa;

import java.util.List;

@Mapper(uses = {
        RoleMapper.class,
        DateMapper.class
})
public interface UserMapper {

    User toDTO(UserJpa entity);

    List<User> toDTOs(List<UserJpa> entities);

    UserJpa toJpa(User dto);

    List<UserJpa> toJPAs(List<User> dtos);

    UserJpa toRegisterJpa(RegisterRequest entity);

    User toRegisterDTO(RegisterRequest entity);

}
