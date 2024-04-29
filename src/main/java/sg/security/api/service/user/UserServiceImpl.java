package sg.security.api.service.user;

import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.security.api.dto.user.ChangePassword;
import sg.security.api.dto.user.User;
import sg.security.api.entity.user.UserJpa;
import sg.security.api.exception.UserNotFoundException;
import sg.security.api.mapper.UserMapper;
import sg.security.api.repository.user.UserJpaRepository;

import java.util.List;

import static sg.security.api.constant.Errors.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final @NonNull UserJpaRepository repository;
    private final @NonNull PasswordEncoder passwordEncoder;
    private final @NonNull UserMapper mapper;

    @Override
    public List<User> findAllUsers() {
        return repository.findAllUsers().stream().map(mapper::toDTO).toList();
    }

    @Override
    public User findById(Integer userId) {
        return repository.findById(userId).map(mapper::toDTO).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDTO).orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username).map(mapper::toDTO).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    @Transactional
    public void update(Integer userId, User user) {

        UserJpa request = repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        request.setFirstname(user.getFirstname());
        request.setLastname(user.getLastname());
        request.setEmail(user.getEmail());
        request.setBirthdate(user.getBirthdate());

        repository.save(request);
    }

    @Override
    @Transactional
    public void updateIsEnabled(Integer userId) {
        repository.updateEnabled(userId);
    }

    @Override
    @Transactional
    public void changePassword(ChangePassword request) {

        var user = (UserJpa) (SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ValidationException(WRONG_PASSWORD);
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new ValidationException(DIFFERENT_PASSWORD);
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new ValidationException(PASSWORD_NOT_MATCHING);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        repository.updatePassword(user.getId(), user.getPassword());
    }

    @Override
    @Transactional
    public void delete(String username) {

        var user = repository
                .findByUsername(username).map(mapper::toDTO).orElseThrow(() -> new UserNotFoundException(username));

        repository.deleteById(user.getId());
    }

    @Override
    @Transactional
    public void delete(Integer userId) {
        repository.deleteById(userId);
    }

}
