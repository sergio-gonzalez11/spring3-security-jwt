package sg.security.api.data;

import sg.security.api.dto.RegisterRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class RegisterRequestDTOData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, RegisterRequest> dataMap;

    public RegisterRequestDTOData() {

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
        final RegisterRequest element = new RegisterRequest();
        element.setFirstname("FIRSTNAME_" + nextId);
        element.setLastname("LASTNAME_" + nextId);
        element.setUsername("USERNAME_" + nextId);
        element.setPassword("PASSWORD_" + nextId);
        element.setEmail("email_" + nextId + "@gmail.com");
        element.setBirthdate(LocalDate.now());
        return element;
    }

    public RegisterRequest get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<RegisterRequest> getList() {
        return this.dataMap.values().stream().toList();
    }
}
