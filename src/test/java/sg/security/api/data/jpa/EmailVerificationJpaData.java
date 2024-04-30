package sg.security.api.data.jpa;

import sg.security.api.entity.email.EmailVerificationJpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class EmailVerificationJpaData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, EmailVerificationJpa> dataMap;

    public EmailVerificationJpaData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final EmailVerificationJpa item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private EmailVerificationJpa generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        final EmailVerificationJpa element = new EmailVerificationJpa();
        element.setId(nextId);
        element.setToken("token_" + nextId);
        element.setExpirationTime(LocalDateTime.now());
        return element;
    }

    public EmailVerificationJpa get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<EmailVerificationJpa> getList() {
        return this.dataMap.values().stream().toList();
    }

}
