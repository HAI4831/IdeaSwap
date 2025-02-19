package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.RoleRequest;
import nvh.run.ideaswap.data.entity.Roles;
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

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RoleService {
    RoleRepository roleRepository;

//    @Cacheable(value = "roles",key = "'page:' + #page + ':size:' + #size")
    public Page<Roles> getAllRoles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Roles> roles;
        try {
            roles = roleRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all roles failed",e);
        }
        return roles;
    }
//    @Cacheable(value = "roles",sync = true)// không nên cache cho get All vì
    public List<Roles> getAllRoles() {
        List<Roles> roles;
        try {
            roles = roleRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all roles failed",e);
        }
        return roles;
    }

    @Cacheable(value = "role",key = "#id",condition = "#id!=null")
    public Roles getRoleById(String id) {
        Roles role ;
        try {
            role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get role by id failed",e);
        }
        return role;
    }

    @Cacheable(value = "role",key = "#name",condition = "#name!=null")
    public Roles findByName(String name) {
        Roles role;
        try {
            role= roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get role by name failed",e);
        }
        return role;
    }

    @CachePut(value = "role",key = "#roleRequest.id",condition = "#roleRequest.id!=null")
    public Roles createRole(RoleRequest roleRequest) {
        Roles role;
        try {
            role = roleRepository.save(
                    Roles.builder()
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

    @CachePut(value = "role",key = "#roleRequest.id",condition = "#roleRequest.id!=null")
    public Roles updateRole(String id, RoleRequest roleRequest) {
        Roles roleUpdating;
        roleUpdating=getRoleById(id); // Kiểm tra role tồn tại
        Roles roleUpdated;
        try {
            roleUpdated = roleRepository.save(
                    Roles.builder()
                            .id(id)
                            .name(roleRequest.name())
                            .createdAt(roleUpdating.getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Update role failed",e);
        }
        return roleUpdated;
    }

    @CacheEvict(value = "role",key = "#id",condition = "#id!=null")
    public Roles deleteRole(String id) {
        Roles role = getRoleById(id);
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete role failed",e);
        }
        return role;
    }
}
