package sg.security.api.config.bbdd;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import sg.security.api.entity.role.RoleEnum;
import sg.security.api.entity.role.RoleJpa;
import sg.security.api.repository.RoleJpaRepository;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleSeeder.class);

    private final @NonNull RoleJpaRepository roleJpaRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRolesBbdd();
    }

    private void loadRolesBbdd() {

        RoleEnum[] roleNames = new RoleEnum[]{
                RoleEnum.BASIC,
                RoleEnum.USER,
                RoleEnum.ADMIN
        };

        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.BASIC, RoleEnum.BASIC.getRoleDescription(),
                RoleEnum.USER, RoleEnum.USER.getRoleDescription(),
                RoleEnum.ADMIN, RoleEnum.ADMIN.getRoleDescription()
        );

        Arrays.stream(roleNames).forEach((roleName) -> {

            Optional<RoleJpa> roleJpa = roleJpaRepository.findByName(roleName.getRoleName());

            roleJpa.ifPresentOrElse((role) -> LOGGER.debug("Roles already exists: {}", role.getName()), () -> {

                LOGGER.debug("Insert Roles: {}", roleName.getRoleName());

                RoleJpa create = new RoleJpa();
                create.setName(roleName.getRoleName());
                create.setDescription(roleDescriptionMap.get(roleName));

                roleJpaRepository.save(create);

            });
        });
    }

}
