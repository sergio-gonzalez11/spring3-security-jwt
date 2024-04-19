package sg.security.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sg.security.api.entity.user.UserJpa;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserJpa, Integer> {

    @Query("SELECT u FROM UserJpa u WHERE u.deactivatedDate is null")
    List<UserJpa> findAllUsers();

    Optional<UserJpa> findByEmail(String email);

    Optional<UserJpa> findByUsername(String username);

    Optional<UserJpa> findByUsernameAndEmail(String username, String email);

    @Modifying
    @Query("update UserJpa u set u.password = ?2 where u.id = ?1")
    void updatePassword(Integer idUser, String password);

    @Modifying
    @Query("update UserJpa u set u.isEnabled = true where u.id = ?1")
    void updateEnabled(Integer idUser);

}
