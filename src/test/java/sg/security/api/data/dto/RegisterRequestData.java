package sg.security.api.data.dto;

import sg.security.api.dto.auth.RegisterRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class RegisterRequestData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, RegisterRequest> dataMap;

    public RegisterRequestData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final RegisterRequest item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private RegisterRequest generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        return RegisterRequest.builder()
                .firstname("FIRSTNAME_" + nextId)
                .lastname("LASTNAME_" + nextId)
                .username("USERNAME_" + nextId)
                .password("PASSWORD_" + nextId)
                .email("email_" + nextId + "@gmail.com")
                .birthdate(LocalDate.now())
                .build();
    }

    public RegisterRequest get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<RegisterRequest> getList() {
        return this.dataMap.values().stream().toList();
    }
}
