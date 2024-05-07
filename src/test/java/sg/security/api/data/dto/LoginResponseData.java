package sg.security.api.data.dto;

import sg.security.api.dto.auth.LoginResponse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class LoginResponseData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, LoginResponse> dataMap;

    public LoginResponseData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final LoginResponse item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private LoginResponse generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        return LoginResponse.builder()
                .token("token_" + nextId)
                .expiresIn(nextId)
                .build();
    }

    public LoginResponse get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<LoginResponse> getList() {
        return this.dataMap.values().stream().toList();
    }
}
