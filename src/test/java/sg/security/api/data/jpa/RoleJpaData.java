package sg.security.api.data.jpa;

import sg.security.api.entity.role.RoleJpa;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class RoleJpaData {

    private final AtomicInteger keyGenerator;

    private final Map<Integer, RoleJpa> dataMap;

    public RoleJpaData() {

        this.keyGenerator = new AtomicInteger(1);
        this.dataMap = new ConcurrentHashMap<>();
        IntStream.range(0, 5)
                .forEach(i -> {
                    final Integer key = this.keyGenerator.get();
                    final RoleJpa item = this.generate();
                    this.dataMap.put(key, item);
                });
    }

    private RoleJpa generate() {
        final Integer nextId = this.keyGenerator.getAndIncrement();
        final RoleJpa element = new RoleJpa();
        element.setId(nextId);
        element.setName("NAME_" + nextId);
        element.setDescription("DESCRIPTION_" + nextId);
        return element;
    }

    public RoleJpa get(final Integer id) {
        return this.dataMap.get(id);
    }

    public List<RoleJpa> getList() {
        return this.dataMap.values().stream().toList();
    }

}
