package sg.security.api.data;

import sg.security.api.dto.EmailVerification;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class EmailVerificationData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, EmailVerification> dataMap;

    public EmailVerificationData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final EmailVerification item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private EmailVerification generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        return EmailVerification.builder()
                .id(nextId)
                .token("TOKEN_" + nextId)
                .expirationTime(new Date())
                .build();
    }

    public EmailVerification get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<EmailVerification> getList() {
        return this.dataMap.values().stream().toList();
    }

}
