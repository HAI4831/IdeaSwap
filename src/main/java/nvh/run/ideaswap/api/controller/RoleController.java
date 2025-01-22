package nvh.run.ideaswap.api.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Roles;
import nvh.run.ideaswap.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @GetMapping
    public ResponseEntity<Object> getAllRoles() {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Roles successfully",
                        "roles", roleService.getAllRoles()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoleById(@PathVariable("id") String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve Role By ID successfully",
                        "role", roleService.getRoleById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createRole(@Validated @RequestBody Roles role) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Create Role successfully",
                        "role", roleService.createRole(role)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRole(@PathVariable("id") String id, @Validated @RequestBody Roles role) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Update Role successfully",
                        "role", roleService.updateRole(id, role)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable("id") String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Delete Role successfully",
                        "role", roleService.deleteRole(id)
                )
        );
    }
}

