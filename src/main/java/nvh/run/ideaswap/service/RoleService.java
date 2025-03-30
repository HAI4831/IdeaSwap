package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.RoleRequest;
import nvh.run.ideaswap.data.entity.Role;
import nvh.run.ideaswap.data.repository.RoleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService {
    RoleRepository roleRepository;

//    @Cacheable(value = "roles",key = "'page:' + #page + ':size:' + #size")
    public Page<Role> getAllRoles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> roles;
        try {
            roles = roleRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all roles failed",e);
        }
        return roles;
    }
//    @Cacheable(value = "roles",sync = true)// không nên cache cho get All vì
    public List<Role> getAllRoles() {
        List<Role> roles;
        try {
            roles = roleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all roles failed",e);
        }
        return roles;
    }

    @Cacheable(value = "role",key = "#id",condition = "#id!=null")
    public Role getRoleById(String id) {
        Role role ;
        try {
            role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get role by id failed",e);
        }
        return role;
    }

    @Cacheable(value = "role",key = "#name",condition = "#name!=null")
    public Role findByName(String name) {
        Role role;
        try {
            role= roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get role by name failed",e);
        }
        return role;
    }

    @CachePut(value = "role",key = "#roleRequest.id",condition = "#roleRequest.id!=null")
    public Role createRole(RoleRequest roleRequest) {
        Role role;
        try {
            role = roleRepository.save(
                    Role.builder()
                            .id(roleRequest.id())
                            .name(roleRequest.name())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create role failed",e);
        }
        return role;
    }

    @CachePut(value = "role", key = "#id", condition = "#id != null")
    public Role updateRole(String id, RoleRequest roleRequest) {
        // Lấy Role hiện tại từ DB
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));

        // Cập nhật các trường nếu có giá trị mới, giữ nguyên nếu null
        Optional.ofNullable(roleRequest.name()).ifPresent(existingRole::setName);
        existingRole.setUpdatedAt(LocalDateTime.now()); // Chỉ cập nhật thời gian sửa đổi

        return roleRepository.save(existingRole);
    }


    @CacheEvict(value = "role",key = "#id",condition = "#id!=null")
    public Role deleteRole(String id) {
        Role role = getRoleById(id);
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete role failed",e);
        }
        return role;
    }
}
