package sg.security.api.service.user;

import sg.security.api.dto.user.ChangePassword;
import sg.security.api.dto.user.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findById(Integer userId);

    User findByEmail(String email);

    User findByUsername(String username);

    void update(Integer userId, User user);

    void updateIsEnabled(Integer userId);

    void changePassword(ChangePassword request);

    void delete(String username);

    void delete(Integer userId);

}
