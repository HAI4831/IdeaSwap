package nvh.run.ideaswap.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ManagerRequest;
import nvh.run.ideaswap.data.entity.Manager;
import nvh.run.ideaswap.data.entity.Role;
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
import java.util.Optional;

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
    public Page<Manager> getAllManagers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        try {
            return managerRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List of Managers failed", e);
        }
    }
//    @Cacheable(value="managers")
    public List<Manager> getAllManagers() {
        try {
            return managerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List of Managers failed", e);
        }
    }

    @Cacheable(value="manager",key="#id",condition = "#id!=null")
    public Manager getManagerById(String id) {
        try {
            return managerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve Manager By ID failed", e);
        }
    }

    @Cacheable(value="manager",key="#username",condition = "#username!=null")
    public Manager findManagerByUsername(String username) {
        try {
            return managerRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve Manager By username "+username+" failed", e);
        }
    }

    @CachePut(value="manager",key="#managerRequest._id",condition = "#managerRequest._id!=null")
    public Manager createManager(@Valid ManagerRequest managerRequest) {
        Role role = roleService.findByName("manager");
        try {
            if(managerRepository.findByUsername(managerRequest.getUsername()).isPresent()) {
                throw new RuntimeException("Manager already exists");
            }
            if(managerRepository.findByEmail(managerRequest.getEmail()).isPresent()){
                throw new RuntimeException("Email already exists");
            }
            if(managerRepository.findByPhoneNumber(managerRequest.getPhoneNumber()).isPresent()){
                throw new RuntimeException("Phone number already exists");
            }


            // Xử lý avatar: Nếu null thì sử dụng ảnh mặc định
            String imageUrl = Optional.ofNullable(managerRequest.getAvatarBase64())
                    .map(avatar -> cloudinaryService.uploadImage(avatar, null, "avatarManager"))
                    .orElse("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg");

            // Xây dựng đối tượng Manager với giá trị mặc định nếu trường đó null
            Manager manager = Manager.builder()
                    .username(managerRequest.getUsername()) // Giữ nguyên username
                    .password(Optional.ofNullable(managerRequest.getPassword())
                            .map(passwordEncoder::encode)
                            .orElse(passwordEncoder.encode("abCD@1234"))) // Mật khẩu mặc định nếu null
                    .roleID(role.getId()) // Giữ nguyên roleID
                    .firstName(Optional.ofNullable(managerRequest.getFirstName()).orElse("")) // Nếu null, gán chuỗi rỗng
                    .lastName(Optional.ofNullable(managerRequest.getLastName()).orElse(""))
                    .email(managerRequest.getEmail()) // Email bắt buộc nên không cần kiểm tra null
                    .phoneNumber(Optional.ofNullable(managerRequest.getPhoneNumber()).orElse(""))
                    .address(Optional.ofNullable(managerRequest.getAddress()).orElse("Ninh Bình")) // Địa chỉ mặc định
                    .avatar(imageUrl)
                    .birthday(managerRequest.getBirthday()) // Có thể null, không cần mặc định
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

//            if (existsByEmail(manager.getEmail()))
//                throw new RuntimeException("Email already exists");
//            if (existsByPhoneNumber(manager.getPhoneNumber()))
//                throw new RuntimeException("Phone number already exists");
//            if (existsByUsername(manager.getUsername()))
//                throw new RuntimeException("Username already exists");
            return managerRepository.save(manager);
        } catch (Exception e) {
            throw new RuntimeException("Create Manager failed", e);
        }
    }

    @CachePut(value = "manager", key = "#id")
    public Manager updateManager(String id, ManagerRequest managerRequest) {
        // Lấy thông tin Manager hiện tại
        Manager existingManager = getManagerById(id);

        try {
            // Nếu có ảnh mới thì upload, ngược lại giữ nguyên
            String imageUrl = Optional.ofNullable(managerRequest.getAvatarBase64())
                    .map(avatar -> cloudinaryService.uploadImage(avatar, null, "avatarManager"))
                    .orElse(existingManager.getAvatar());

            // Cập nhật các trường nếu có giá trị, giữ nguyên nếu không có
            existingManager.setFirstName(Optional.ofNullable(managerRequest.getFirstName()).orElse(existingManager.getFirstName()));
            existingManager.setLastName(Optional.ofNullable(managerRequest.getLastName()).orElse(existingManager.getLastName()));
            existingManager.setEmail(Optional.ofNullable(managerRequest.getEmail()).orElse(existingManager.getEmail()));
            existingManager.setPhoneNumber(Optional.ofNullable(managerRequest.getPhoneNumber()).orElse(existingManager.getPhoneNumber()));
            existingManager.setAddress(Optional.ofNullable(managerRequest.getAddress()).orElse(existingManager.getAddress()));

            // Nếu có password mới thì encode, không thì giữ nguyên
            Optional.ofNullable(managerRequest.getPassword())
                    .ifPresent(password -> existingManager.setPassword(passwordEncoder.encode(password)));

            existingManager.setAvatar(imageUrl);
            existingManager.setBirthday(Optional.ofNullable(managerRequest.getBirthday()).orElse(existingManager.getBirthday()));
            existingManager.setUpdatedAt(LocalDateTime.now()); // Chỉ cập nhật thời gian cập nhật

            return managerRepository.save(existingManager);
        } catch (Exception e) {
            throw new RuntimeException("Update Manager failed", e);
        }
    }


//    @CachePut(value="manager",key="#id")
//    public Manager updateManager(String id, ManagerRequest managerRequest) {
//        getManagerById(id);
//        Role role = roleService.getRoleById(managerRequest.getRoleID());
//
//        try {
//            String imageUrl = cloudinaryService.uploadImage(managerRequest.getAvatar(),null,"avatarManager");
//
//            Manager manager = Manager.builder()
//                    .id(managerRequest.get_id())
//                    .roleID(role.getId())
//                    .firstName(managerRequest.getFirstName())
//                    .lastName(managerRequest.getLastName())
//                    .email(managerRequest.getEmail())
//                    .phoneNumber(managerRequest.getPhoneNumber())
//                    .address(managerRequest.getAddress())
//                    .password(passwordEncoder.encode(managerRequest.getPassword()))
//                    .avatar(imageUrl)
//                    .birthday(managerRequest.getBirthday())
//                    .createdAt(LocalDateTime.now())
//                    .updatedAt(LocalDateTime.now())
//                    .build();
//            manager.setId(id);
//            return managerRepository.save(manager);
//        } catch (Exception e) {
//            throw new RuntimeException("Update Manager failed", e);
//        }
//    }

    @CacheEvict(value="manager",key="#id")
    public Manager deleteManager(String id) {
        Manager manager = getManagerById(id);
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
