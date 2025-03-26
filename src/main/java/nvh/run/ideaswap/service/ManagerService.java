package nvh.run.ideaswap.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ManagerRequest;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.entity.Roles;
import nvh.run.ideaswap.data.repository.IManagerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ManagerService {
    static Logger log = LoggerFactory.getLogger(ManagerService.class);
    RoleService roleService;
    PasswordEncoder passwordEncoder;

    IManagerRepository managerRepository;
    private final CloudinaryService cloudinaryService;

//    @Cacheable(value = "managers",key = "'page:' + #page + ':size:' + #size")
    public Page<Managers> getAllManagers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            return managerRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List of Managers failed", e);
        }
    }
//    @Cacheable(value="managers")
    public List<Managers> getAllManagers() {
        try {
            return managerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List of Managers failed", e);
        }
    }

    @Cacheable(value="manager",key="#id",condition = "#id!=null")
    public Managers getManagerById(String id) {
        try {
            return managerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve Manager By ID failed", e);
        }
    }
    @Cacheable(value="manager",key="#username",condition = "#username!=null")
    public Managers findManagerByUsername(String username) {
        try {
            return managerRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve Manager By username "+username+" failed", e);
        }
    }

    @CachePut(value="manager",key="#managerRequest._id",condition = "#managerRequest._id!=null")
    public Managers createManager(@Valid ManagerRequest managerRequest) {
        Roles role = roleService.getRoleById(managerRequest.getRoleID());

        try {
            String imageUrl = cloudinaryService.uploadImage(managerRequest.getAvatar(),null);
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
            Managers manager = Managers.builder()
                    .id(managerRequest.get_id())
                    .roleID(role.getId())
                    .firstName(managerRequest.getFirstName())
                    .lastName(managerRequest.getLastName())
                    .email(managerRequest.getEmail())
                    .phoneNumber(managerRequest.getPhoneNumber())
                    .address(managerRequest.getAddress())
                    .password(passwordEncoder.encode(managerRequest.getPassword()))
                    .avatar(imageUrl)
                    .birthday(managerRequest.getBirthday())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            if (existsByEmail(manager.getEmail()))
                throw new RuntimeException("Email already exists");
            if (existsByPhoneNumber(manager.getPhoneNumber()))
                throw new RuntimeException("Phone number already exists");
            if (existsByUsername(manager.getUsername()))
                throw new RuntimeException("Username already exists");
            return managerRepository.save(manager);
        } catch (Exception e) {
            throw new RuntimeException("Create Manager failed", e);
        }
    }

    @CachePut(value="manager",key="#id")
    public Managers updateManager(String id, ManagerRequest managerRequest) {
        getManagerById(id);
        Roles role = roleService.getRoleById(managerRequest.getRoleID());

        try {
            String imageUrl = cloudinaryService.uploadImage(managerRequest.getAvatar(),null);
            if (imageUrl == null) {
                throw new RuntimeException("Course image upload failed");
            }
            Managers manager = Managers.builder()
                    .id(managerRequest.get_id())
                    .roleID(role.getId())
                    .firstName(managerRequest.getFirstName())
                    .lastName(managerRequest.getLastName())
                    .email(managerRequest.getEmail())
                    .phoneNumber(managerRequest.getPhoneNumber())
                    .address(managerRequest.getAddress())
                    .password(passwordEncoder.encode(managerRequest.getPassword()))
                    .avatar(imageUrl)
                    .birthday(managerRequest.getBirthday())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            manager.setId(id);
            return managerRepository.save(manager);
        } catch (Exception e) {
            throw new RuntimeException("Update Manager failed", e);
        }
    }

    @CacheEvict(value="manager",key="#id")
    public Managers deleteManager(String id) {
        Managers manager = getManagerById(id);
        try {
            managerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete Manager failed", e);
        }
        return manager;
    }

    public boolean existsByEmail(String email) {
        return managerRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return managerRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean existsByUsername(String username) {
        return managerRepository.existsByUsername(username);
    }
}
