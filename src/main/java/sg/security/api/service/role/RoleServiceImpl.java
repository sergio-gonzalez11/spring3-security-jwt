package sg.security.api.service.role;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.security.api.dto.role.Role;
import sg.security.api.exception.RoleNotFoundException;
import sg.security.api.mapper.RoleMapper;
import sg.security.api.repository.role.RoleJpaRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final @NonNull RoleJpaRepository roleJpaRepository;
    private final @NonNull RoleMapper mapper;

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<Role> findByIds(List<Integer> ids) {
        return roleJpaRepository.findAllByIdIn(ids).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public Role findByRoleId(Integer idRole) {
        return roleJpaRepository.findById(idRole)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RoleNotFoundException(idRole));
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleJpaRepository.findByName(roleName)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleJpaRepository.save(mapper.toJPA(role));
    }

    @Override
    @Transactional
    public void update(Role role) throws Exception {

        var request = roleJpaRepository.findById(role.getId())
                .orElseThrow(() -> new RoleNotFoundException(role.getName()));

        request.setName(role.getName());
        request.setDescription(role.getDescription());

        roleJpaRepository.save(request);

    }

    @Override
    @Transactional
    public void delete(Integer roleId) {
        roleJpaRepository.deleteById(roleId);
    }
}
