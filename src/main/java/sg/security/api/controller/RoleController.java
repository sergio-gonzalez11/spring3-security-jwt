package sg.security.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import sg.security.api.dto.role.CreateRole;
import sg.security.api.dto.role.Role;
import sg.security.api.mapper.RoleMapper;
import sg.security.api.service.role.RoleService;
import sg.security.api.shared.AdminAccess;

import java.util.List;

@Slf4j
@AdminAccess
@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final @NonNull RoleService roleService;

    private final @NonNull RoleMapper mapper;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        log.info("[RoleController] Start getAllRoles");
        return ResponseEntity.ok(roleService.findAll());
    }


    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@Valid @PathVariable("roleId") Integer roleId) {
        log.info("[RoleController] Start getRoleById: {}", roleId);
        return ResponseEntity.ok(roleService.findByRoleId(roleId));
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(@Valid @PathVariable("name") String name) {
        log.info("[RoleController] Start getRoleByName: {}", name);
        return ResponseEntity.ok(roleService.findByRoleName(name));
    }


    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateRole createRole) {
        log.info("[RoleController] Start create: {}", createRole);
        roleService.save(mapper.toDTO(createRole));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> delete(@Valid @PathVariable("roleId") Integer roleId) {
        log.info("[RoleController] Start delete: {}", roleId);
        roleService.delete(roleId);
        return ResponseEntity.noContent().build();
    }

}
