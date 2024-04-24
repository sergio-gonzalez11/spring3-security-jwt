package sg.security.api.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import sg.security.api.entity.role.RoleJpa;

import java.util.List;
import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleJpa, Integer> {

    Optional<RoleJpa> findByName(String name);

    List<RoleJpa> findAllByIdIn(List<Integer> ids);

}