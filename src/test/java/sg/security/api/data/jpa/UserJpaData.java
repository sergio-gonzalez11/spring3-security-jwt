package sg.security.api.data.jpa;

import org.springframework.security.crypto.password.PasswordEncoder;
import sg.security.api.config.security.Argon2PasswordEncoder;
import sg.security.api.entity.user.UserJpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class UserJpaData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, UserJpa> dataMap;


    private final PasswordEncoder passwordEncoder;

    public UserJpaData() {

        this.passwordEncoder = new Argon2PasswordEncoder();
        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final UserJpa item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private UserJpa generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        final UserJpa element = new UserJpa();
        element.setId(nextId);
        element.setFirstname("firstname_" + nextId);
        element.setLastname("lastname_" + nextId);
        element.setUsername("username_" + nextId);
        element.setPassword(this.passwordEncoder.encode("password_" + nextId));
        element.setEmail("email_" + nextId + "@gmail.com");
        element.setBirthdate(LocalDate.now());
        return element;
    }

    public UserJpa get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<UserJpa> getList() {
        return this.dataMap.values().stream().toList();
    }

}
