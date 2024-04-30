package sg.security.api.data.dto;

import sg.security.api.dto.role.Role;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class RoleData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, Role> dataMap;

    public RoleData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final Role item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private Role generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        return Role.builder()
                .id(nextId)
                .name("name_" + nextId)
                .description("description_" + nextId)
                .build();
    }

    public Role get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<Role> getList() {
        return this.dataMap.values().stream().toList();
    }

}
