package sg.security.api.data.dto;

import sg.security.api.dto.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class UserData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, User> dataMap;

    public UserData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final User item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private User generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        return User.builder()
                .id(nextId)
                .firstname("FIRSTNAME_" + nextId)
                .lastname("LASTNAME_" + nextId)
                .username("USERNAME_" + nextId)
                .password("PASSWORD_" + nextId)
                .email("EMAIL_" + nextId)
                .birthdate(LocalDate.now())
                .build();
    }

    public User get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<User> getList() {
        return this.dataMap.values().stream().toList();
    }

}
