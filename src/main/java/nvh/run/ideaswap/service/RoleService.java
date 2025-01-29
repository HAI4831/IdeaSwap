package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Roles;
import nvh.run.ideaswap.data.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService {
    RoleRepository roleRepository;

    public List<Roles> getAllRoles() {
        List<Roles> roles;
        try {
            roles = roleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all roles failed",e);
        }
        return roles;
    }

    public Roles getRoleById(String id) {
        Roles role ;
        try {
            role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get role by id failed",e);
        }
        return role;
    }

    public Roles createRole(Roles role) {
        try {
            role = roleRepository.save(role);
        } catch (Exception e) {
            throw new RuntimeException("Create role failed",e);
        }
        return role;
    }

    public Roles updateRole(String id, Roles role) {
        getRoleById(id); // Kiểm tra role tồn tại
        role.setId(id);
        Roles roleUpdated;
        try {
            roleUpdated = roleRepository.save(role);
        } catch (Exception e) {
            throw new RuntimeException("Update role failed",e);
        }
        return roleUpdated;
    }

    public Roles deleteRole(String id) {
        Roles role = getRoleById(id);
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete role failed",e);
        }
        return role;
    }

    public Roles findByName(String name) {
        Roles role;
        try {
           role= roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get role by name failed",e);
        }
        return role;
    }
}
