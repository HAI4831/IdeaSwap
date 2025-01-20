package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
public class RoleService {
    RoleRepository roleRepository;

    public RoleDTO getAllRoles() {
        List<Roles> roles;
        try {
            roles = roleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all roles failed",e);
        }
        return RoleDTO.builder().build();
    }

    public RoleDTO getRoleById(String id) {
        Roles role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return RoleDTO.builder().build();
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Roles roleSaved = roleRepository.save(
                Roles.builder()
                        .name(roleDTO.getName())
                        .build()
        );
        return RoleDTO.builder().build();
    }

    public RoleDTO updateRole(String id, RoleDTO roleDTO) {
        getRoleById(id); // Kiểm tra role tồn tại
        Roles roleUpdated = roleRepository.save(
                Roles.builder()
                        .id(id) // Đảm bảo ID không bị thay đổi
                        .name(roleDTO.getName())
                        .build()
        );
        return RoleDTO.builder().build();
    }

    public RoleDTO deleteRole(String id) {
        ResponseEntity<Object> response = getRoleById(id);
        Map<String, Object> responseBody = new HashMap<>((Map<String, Object>) response.getBody());
        responseBody.put("message", "Delete Role successfully");
        roleRepository.deleteById(id);
        return RoleDTO.builder().build();
    }

    public Roles findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
