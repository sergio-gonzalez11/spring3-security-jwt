package sg.security.api.data;

import sg.security.api.dto.LoginRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class LoginRequestDTOData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, LoginRequest> dataMap;

    public LoginRequestDTOData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final LoginRequest item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private LoginRequest generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        final LoginRequest element = new LoginRequest();
        element.setUsername("username_" + nextId);
        element.setPassword("password_" + nextId);
        return element;
    }

    public LoginRequest get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<LoginRequest> getList() {
        return this.dataMap.values().stream().toList();
    }
}
