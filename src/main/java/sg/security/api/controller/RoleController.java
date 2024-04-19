package sg.security.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import sg.security.api.dto.CreateRole;
import sg.security.api.dto.Role;
import sg.security.api.mapper.RoleMapper;
import sg.security.api.service.role.RoleService;
import sg.security.api.shared.AdminAccess;

import java.util.List;

@AdminAccess
@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final @NonNull RoleService roleService;

    private final @NonNull RoleMapper mapper;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }


    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable("roleId") Integer roleId) {
        return ResponseEntity.ok(roleService.findByRoleId(roleId));
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(roleService.findByRoleName(name));
    }


    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateRole createRole) {
        roleService.save(mapper.toDTO(createRole));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> delete(@PathVariable("roleId") Integer roleId) {
        roleService.delete(roleId);
        return ResponseEntity.noContent().build();
    }

}
