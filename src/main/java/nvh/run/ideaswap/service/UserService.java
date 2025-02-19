package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.exceptions.exception.ExceptionWrapper;
import nvh.run.ideaswap.common.exceptions.exception.custom.auth.UsernameAlreadyExistException;
import nvh.run.ideaswap.data.dto.UserRequest;
import nvh.run.ideaswap.data.entity.Codes;
import nvh.run.ideaswap.data.entity.Roles;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.IUserRepository;
import org.springframework.cache.annotation.CacheEvict;
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
public class UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    IUserRepository iUserRepository;
    RoleService roleService;
    PasswordEncoder passwordEncoder;
    CloudinaryService cloudinaryService;
    CodeService codeService;

//    @Cacheable(value = "users",key = "'page:' + #page + ':size:' + #size")
    public Page<Users> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Users> users ;
        try {
            users = iUserRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Users failed",e);
        }
        return users;
    }
//    @Cacheable(value="users")
    public List<Users> getAllUsers() {
        List<Users> users ;
        try {
            users = iUserRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Users failed",e);
        }
        return users;
    }

    @Cacheable(value = "user", key = "#id", condition = "#id != null")
    public Users getUserById(String id) {
        return getUserByIdInternal(id); // Tách logic ra khỏi @Cacheable
    }
    //thử dùng wrapper
    private Users getUserByIdInternal(String id) {
        return ExceptionWrapper.RuntimeWrapper(() ->
                        iUserRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("User not found")),
                "Retrieve User By ID failed"
        );
    }
//    @CachePut(value="user",key="#userRequest.id",condition = "#userRequest.id!=null")
//    public Users createUser(UserRequest userRequest) {
//        Users user;
//        try {
//            String imageUrl;
//            if(userRequest.getRating()==null) userRequest.setRating(0);
//            if(userRequest.getAvatar()==null) imageUrl= "https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg";
//            else imageUrl = cloudinaryService.uploadImage(userRequest.getAvatar());
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
//            if(existsByEmail(userRequest.getEmail())) throw new RuntimeException("Email already exist");
//            if(existsByPhoneNumber(userRequest.getPhoneNumber())) throw new RuntimeException("Phone number already exist");
//            if(existsByUsername(userRequest.getUsername())) throw new RuntimeException("Username already exist");
//            Roles role = roleService.getRoleById(userRequest.getRoleID());
//            if(userRequest.getPassword()==null) userRequest.setPassword("$2a$10$ZD/EROx56XOvcutCg9jHxeXrz.iqMstXUCksTyvBb8gfD8SPPm7uW");
//            user = iUserRepository.save(
//                    Users.builder()
//                            .id(userRequest.getId())
//                            .roleID(role.getId())
//                            .email(userRequest.getEmail())
//                            .username(userRequest.getUsername())
//                            .password(userRequest.getPassword()!=null ? passwordEncoder.encode(userRequest.getPassword()):"$2a$10$ZD/EROx56XOvcutCg9jHxeXrz.iqMstXUCksTyvBb8gfD8SPPm7uW")
//                            .firstName(userRequest.getFirstName()!=null ? userRequest.getFirstName():"")
//                            .lastName(userRequest.getLastName()!=null ? userRequest.getLastName():"")
//                            .phoneNumber(userRequest.getPhoneNumber())
//                            .address(userRequest.getAddress()!=null ? userRequest.getAddress():"")
//                            .gender(userRequest.getGender()!=null ? userRequest.getGender(): Gender.male)
//                            .avatar(imageUrl!=null ? imageUrl:"https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
//                            .description(userRequest.getDescription())
//                            .rating(userRequest.getRating()!=null ? userRequest.getRating():0)
//                            .version(2L)
//                            .birthday(userRequest.getBirthday())
//                            .createdAt(LocalDateTime.now())
//                            .updatedAt(LocalDateTime.now())
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("Create User failed",e);
//        }
//        return user;
//    }
    @Cacheable(value="user",key="#id",condition = "#id!=null")
    public Users updateUser(String id, UserRequest userRequest) {
        getUserById(id);
        Roles role = roleService.getRoleById(userRequest.getRoleID());
        Users updatedUser ;
        try {
            String imageUrl = cloudinaryService.uploadImage(userRequest.getAvatar());
            if (imageUrl == null) {
                throw new RuntimeException("Course image upload failed");
            }
            updatedUser = iUserRepository.save(
                    Users.builder()
                            .id(id)
                            .roleID(role.getId())
                            .email(userRequest.getEmail())
                            .username(userRequest.getUsername())
                            .password(passwordEncoder.encode(userRequest.getPassword()))
                            .firstName(userRequest.getFirstName())
                            .lastName(userRequest.getLastName())
                            .phoneNumber(userRequest.getPhoneNumber())
                            .address(userRequest.getAddress())
                            .gender(userRequest.getGender())
                            .avatar(imageUrl)
                            .description(userRequest.getDescription())
                            .rating(userRequest.getRating())
                            .version(2L)
                            .birthday(userRequest.getBirthday())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Update User failed",e);
        }
        return updatedUser;
    }
    @CacheEvict(value="user",key="#id",condition = "#id!=null")
    public Users deleteUser(String id) {
       Users user= getUserById(id);
        try {
            iUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete User failed",e);
        }
        return user;
    }
    @Cacheable(value="user",key="#username",condition = "#username!=null")
    public Users findByUsername(String username) {
        Users user;
        try {
            user=iUserRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found with username:{}"+username));
        }
        catch (Exception e) {
            throw new RuntimeException("Retrieve User By Username failed",e);
        }
        return user;
    }
    @Cacheable(value="user",key="#id",condition = "#id!=null")
    public Users findById(String id) {
        Users user ;
        try {
            user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve User By ID failed",e);
        }
        return user;
    }
    @Cacheable(value="user",key="#phoneNumber",condition = "#phoneNumber!=null")
    public Users findByPhoneNumber(String phoneNumber){
        log.info("Find By Phone Number:{}",phoneNumber);
        Users user ;
        try {
           user = iUserRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new RuntimeException("User not found with phoneNumber:{}"+phoneNumber));
        } catch (RuntimeException e) {
            throw new RuntimeException("Retrieve User By Phone Number failed",e);
        }
        return user;
    }
    @Cacheable(value="user",key="#email",condition = "#email!=null")
    public Users findUserByEmail(String email){
        Users user ;
        try {
            user = iUserRepository.findUsersByEmail(email);
        } catch (RuntimeException e) {
            throw new RuntimeException("Retrieve User By Email failed",e);
        }
        return user;
    }
    public boolean existsByPhoneNumber(String phoneNumber) {
        return iUserRepository.existsByPhoneNumber(phoneNumber);
    }
    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username).orElseThrow(()->new UsernameAlreadyExistException("User not found with username:{}"+username));
    }
    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }

    public Users forgetPassword(String email, String username) {
        Users user;
        try {
            user = iUserRepository.findUsersByEmailOrUsernameContains(email,username);
        } catch (RuntimeException e) {
            throw new RuntimeException("Retrieve User By Email or Username failed",e);
        }
        return user;
    }

    public Users resetPassword(String id, String email, String newPassword, int code) {
        Users user;
        try {
            Codes checkCode = codeService.verifyCode(email,code);
            user = getUserById(id);
            user.setPassword(passwordEncoder.encode(newPassword));
            user=iUserRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("update new password failed",e);
        }
        return user;
    }
//    public Users createUser(UserRequest userRequest) {
//        Users user;
//        try {
//            String imageUrl = cloudinaryService.uploadImage(userRequest.getAvatar());
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
//            if(existsByEmail(userRequest.getEmail())) throw new RuntimeException("Email already exist");
//            if(existsByPhoneNumber(userRequest.getPhoneNumber())) throw new RuntimeException("Phone number already exist");
//            if(existsByUsername(userRequest.getUsername())) throw new RuntimeException("Username already exist");
//            Roles role = roleService.getRoleById(userRequest.getRoleID());
//            user = iUserRepository.save(
//                    Users.builder()
//                            .id(userRequest.getId())
//                            .roleID(role.getId())
//                            .email(userRequest.getEmail())
//                            .username(userRequest.getUsername())
//                            .password(passwordEncoder.encode(userRequest.getPassword()))
//                            .firstName(userRequest.getFirstName())
//                            .lastName(userRequest.getLastName())
//                            .phoneNumber(userRequest.getPhoneNumber())
//                            .address(userRequest.getAddress())
//                            .gender(userRequest.getGender())
//                            .avatar(imageUrl)
//                            .description(userRequest.getDescription())
//                            .rating(userRequest.getRating())
//                            .version(2L)
//                            .birthday(userRequest.getBirthday())
//                            .createdAt(LocalDateTime.now())
//                            .updatedAt(LocalDateTime.now())
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("Create User failed",e);
//        }
//        return user;
//    }
}
