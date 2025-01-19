package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.IRoleService;
import nvh.run.ideaswap.data.dto.RoleDTO;
import nvh.run.ideaswap.data.entity.Roles;
import nvh.run.ideaswap.data.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<Object> getAllRoles() {
        List<Roles> roles;
        try {
            roles = roleRepository.findAll();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "success", false,
                            "message", "Retrieve List Roles failed",
                            "error", e.getMessage()
                    )
            );
        }
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Roles successfully",
                        "roles", roles
                )
        );
    }

    @Override
    public ResponseEntity<Object> getRoleById(String id) {
        Roles role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve Role By ID successfully",
                        "role", role
                )
        );
    }

    @Override
    public ResponseEntity<Object> createRole(RoleDTO roleDTO) {
        Roles roleSaved = roleRepository.save(
                Roles.builder()
                        .name(roleDTO.getName())
                        .build()
        );
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Create Role successfully",
                        "role", roleSaved
                )
        );
    }

    @Override
    public ResponseEntity<Object> updateRole(String id, RoleDTO roleDTO) {
        getRoleById(id); // Kiểm tra role tồn tại
        Roles roleUpdated = roleRepository.save(
                Roles.builder()
                        .id(id) // Đảm bảo ID không bị thay đổi
                        .name(roleDTO.getName())
                        .build()
        );
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Update Role successfully",
                        "role", roleUpdated
                )
        );
    }

    @Override
    public ResponseEntity<Object> deleteRole(String id) {
        ResponseEntity<Object> response = getRoleById(id);
        Map<String, Object> responseBody = new HashMap<>((Map<String, Object>) response.getBody());
        responseBody.put("message", "Delete Role successfully");
        roleRepository.deleteById(id);
        return ResponseEntity.status(200).body(responseBody);
    }

    @Override
    public Roles findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
