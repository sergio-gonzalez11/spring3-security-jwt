/*
package sg.security.api.config.bbdd;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sg.security.api.entity.role.RoleEnum;
import sg.security.api.entity.role.RoleJpa;
import sg.security.api.entity.user.UserJpa;
import sg.security.api.repository.RoleJpaRepository;
import sg.security.api.repository.UserJpaRepository;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminSeeder.class);
    private final @NonNull RoleJpaRepository roleJpaRepository;

    private final @NonNull UserJpaRepository userJpaRepository;

    private final @NonNull PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            this.loadAdmin();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAdmin() throws Exception {

        String emailAdmin = "super.admin@gmail.com";

        UserJpa userJpa = userJpaRepository.findByEmail(emailAdmin).orElse(null);

        if (userJpa != null) {
            LOGGER.info("Admin already created");
            return;
        }


        RoleJpa roleJpa = roleJpaRepository.findByName(RoleEnum.ADMIN.getRoleName()).orElseThrow(() -> new Exception("NotFound"));


        UserJpa create = new UserJpa();
        create.setFirstname("admin");
        create.setLastname("admin");
        create.setEmail("super.admin@gmail.com");
        create.setUsername("admin");
        create.setPassword(passwordEncoder.encode("123456"));
        create.setRole(roleJpa);
        create.setBirthdate(LocalDate.now());
        create.setIsEnabled(Boolean.TRUE);

        userJpaRepository.save(create);

        LOGGER.info("Insert Admin: {}", create);
    }
}
*/
