package sg.security.api.data.dto;

import sg.security.api.dto.user.ChangePassword;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ChangePasswordData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, ChangePassword> dataMap;

    public ChangePasswordData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final ChangePassword item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private ChangePassword generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        return ChangePassword.builder()
                .currentPassword("current_password_" + nextId)
                .newPassword("new_password_" + nextId)
                .confirmationPassword("confirmation_password_" + nextId)
                .build();
    }

    public ChangePassword get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<ChangePassword> getList() {
        return this.dataMap.values().stream().toList();
    }

}
