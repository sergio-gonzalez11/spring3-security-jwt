package sg.security.api.mapper;

import org.mapstruct.Mapper;
import sg.security.api.dto.EmailVerification;
import sg.security.api.entity.emailVerification.EmailVerificationJpa;

import java.util.List;

@Mapper(uses = {
        UserMapper.class,
        DateMapper.class
})
public interface EmailVerificationMapper {

    EmailVerification toDTO(EmailVerificationJpa entity);

    List<EmailVerification> toDTOs(List<EmailVerificationJpa> entities);

    EmailVerificationJpa toJPA(EmailVerification dto);

    List<EmailVerificationJpa> toJPAs(List<EmailVerification> dtos);

}
