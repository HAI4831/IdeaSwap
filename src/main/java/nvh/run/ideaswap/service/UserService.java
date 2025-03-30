package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.dto.UserRequest;
import nvh.run.ideaswap.data.dto.auth.request.RegisterRequest;
import nvh.run.ideaswap.data.entity.Code;
import nvh.run.ideaswap.data.entity.Gender;
import nvh.run.ideaswap.data.entity.Role;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.IUserRepository;
import nvh.run.ideaswap.exceptions.DatabaseException;
import nvh.run.ideaswap.exceptions.UsernameAlreadyExistException;
import nvh.run.ideaswap.exceptions.custom.ExceptionWrapper;
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
import java.util.Optional;

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
    NotificationService notificationService;


//    @Cacheable(value = "users",key = "'page:' + #page + ':size:' + #size")
    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users ;
        try {
            users = iUserRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Users failed",e);
        }
        return users;
    }
//    @Cacheable(value="users")
    public List<User> getAllUsers() {
        List<User> users ;
        try {
            users = iUserRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Users failed",e);
        }
        return users;
    }

    @Cacheable(value = "user", key = "#id", condition = "#id != null")
    public User getUserById(String id) {
        return getUserByIdInternal(id); // Tách logic ra khỏi @Cacheable
    }
    //thử dùng wrapper
    private User getUserByIdInternal(String id) {
        return ExceptionWrapper.RuntimeWrapper(() ->
                        iUserRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("User not found")),
                "Retrieve User By ID failed"
        );
    }
    public User createUser(RegisterRequest registerRequest) {
        Role role = roleService.findByName("user");
        User user;
        try {

            // Kiểm tra username không được trống
            if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty()) {
                throw new RuntimeException("Username cannot be empty.");
            }
            // Kiểm tra username đã tồn tại hay chưa
            if (iUserRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
                throw new RuntimeException("Username already exists.");
            }

            // Kiểm tra email hợp lệ
            if (registerRequest.getEmail() == null || !isValidEmail(registerRequest.getEmail())) {
                throw new RuntimeException("Invalid email format.");
            }
            // Kiểm tra email đã tồn tại
            if (iUserRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists.");
            }

            // Kiểm tra số điện thoại hợp lệ
            if (registerRequest.getPhoneNumber() != null && !registerRequest.getPhoneNumber().trim().isEmpty() && isValidPhoneNumber(registerRequest.getPhoneNumber())) {
                // Kiểm tra phone number đã tồn tại
                if (iUserRepository.findByPhoneNumber(registerRequest.getPhoneNumber()).isPresent()) {
                    throw new RuntimeException("Phone number already in use.");
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while vaildate the user: " + e.getMessage(), e);
        }
        try {

            user= iUserRepository.save(
                    //map
                    User.builder()
                            .username(registerRequest.getUsername())
                            .password(registerRequest.getPassword() ==null ? passwordEncoder.encode("abCD@1234") : passwordEncoder.encode(registerRequest.getPassword()))  // Encode password
                            .firstName(registerRequest.getFirstName()==null ? "":registerRequest.getFirstName())
                            .lastName(registerRequest.getLastName()==null?"":registerRequest.getLastName())
                            .email(registerRequest.getEmail())
                            .roleID(role.getId())
                            .phoneNumber(registerRequest.getPhoneNumber() == null ? null : registerRequest.getPhoneNumber() )
                            .rating(0)
                            .address(registerRequest.getAddress() == null ? "Ninh Bình" : registerRequest.getAddress())
                            .gender(registerRequest.getGender() == null ? Gender.Male : registerRequest.getGender())
                            .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
                            .description(registerRequest.getDescription()==null ? "" :registerRequest.getDescription() )
                            .build());
        }
        catch (Exception e){
            throw new DatabaseException("Register failed for user ", e);
        }
        notificationService.createNotification(
                NotificationRequest.builder()
                        .id(null)
                        .userIDs(List.of(user.getId()))
                        .description("A new user just registered")
                        .imageUrl(user.getAvatar())
                        .build()
        );
        return user;
    }

    @Cacheable(value="user",key="#id",condition = "#id!=null")
    public User updateUser(String id, UserRequest userRequest) {

        User updatedUser = iUserRepository.findById(id).orElseThrow(()-> new RuntimeException("update user failed , user not found with id "+id)) ;
        try {
            // Kiểm tra xem email, username, phoneNumber có bị trùng không
            if (userRequest.getEmail() != null && iUserRepository.existsByEmailAndIdNot(userRequest.getEmail(), id)) {
                throw new RuntimeException("Email already in use by another user.");
            }

            if (userRequest.getUsername() != null && iUserRepository.existsByUsernameAndIdNot(userRequest.getUsername(), id)) {
                throw new RuntimeException("Username already in use by another user.");
            }

            if (userRequest.getPhoneNumber() != null && iUserRepository.existsByPhoneNumberAndIdNot(userRequest.getPhoneNumber(), id)) {
                throw new RuntimeException("Phone number already in use by another user.");
            }


            // Nếu có avatar mới thì upload lên Cloudinary
            if (userRequest.getImageBase64() != null && !userRequest.getImageBase64().isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(userRequest.getImageBase64(), null, "avatar");
                updatedUser.setAvatar(imageUrl);
            }

            // Cập nhật các giá trị nếu chúng không null
            Optional.ofNullable(userRequest.getEmail()).ifPresent(updatedUser::setEmail);
            Optional.ofNullable(userRequest.getUsername()).ifPresent(updatedUser::setUsername);
            Optional.ofNullable(userRequest.getPassword()).map(passwordEncoder::encode).ifPresent(updatedUser::setPassword);
            Optional.ofNullable(userRequest.getFirstName()).ifPresent(updatedUser::setFirstName);
            Optional.ofNullable(userRequest.getLastName()).ifPresent(updatedUser::setLastName);
            Optional.ofNullable(userRequest.getPhoneNumber()).ifPresent(updatedUser::setPhoneNumber);
            Optional.ofNullable(userRequest.getAddress()).ifPresent(updatedUser::setAddress);
            Optional.ofNullable(userRequest.getGender()).ifPresent(updatedUser::setGender);
            Optional.ofNullable(userRequest.getDescription()).ifPresent(updatedUser::setDescription);
            Optional.ofNullable(userRequest.getBirthday()).ifPresent(updatedUser::setBirthday);

            // Cập nhật thời gian chỉnh sửa
            updatedUser.setUpdatedAt(LocalDateTime.now());

            // Lưu lại đối tượng đã được cập nhật
            return iUserRepository.save(updatedUser);

        } catch (Exception e) {
            throw new RuntimeException("Update User failed",e);
        }
    }
    @CacheEvict(value="user",key="#id",condition = "#id!=null")
    public User deleteUser(String id) {
       User user= getUserById(id);
        try {
            iUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete User failed",e);
        }
        return user;
    }
    @Cacheable(value="user",key="#username",condition = "#username!=null")
    public User findByUsername(String username) {
        User user;
        try {
            user=iUserRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found with username:{}"+username));
        }
        catch (Exception e) {
            throw new RuntimeException("Retrieve User By Username failed",e);
        }
        return user;
    }
    @Cacheable(value="user",key="#id",condition = "#id!=null")
    public User findById(String id) {
        User user ;
        try {
            user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve User By ID failed",e);
        }
        return user;
    }
    @Cacheable(value="user",key="#phoneNumber",condition = "#phoneNumber!=null")
    public User findByPhoneNumber(String phoneNumber){
        log.info("Find By Phone Number:{}",phoneNumber);
        User user ;
        try {
           user = iUserRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new RuntimeException("User not found with phoneNumber:{}"+phoneNumber));
        } catch (RuntimeException e) {
            throw new RuntimeException("Retrieve User By Phone Number failed",e);
        }
        return user;
    }
    @Cacheable(value="user",key="#email",condition = "#email!=null")
    public User findUserByEmail(String email){
        User user ;
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

    public User forgetPassword(String email, String username) {
        User user;
        try {
            user = iUserRepository.findUsersByEmailOrUsernameContains(email,username);
        } catch (RuntimeException e) {
            throw new RuntimeException("Retrieve User By Email or Username failed",e);
        }
        return user;
    }

    public User resetPassword(String id, String email, String newPassword, int code) {
        User user;
        try {
            Code checkCode = codeService.verifyCode(email,code);
            user = getUserById(id);
            user.setPassword(passwordEncoder.encode(newPassword));
            user=iUserRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("update new password failed",e);
        }
        return user;
    }
    //_____________helper method
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra không null và khớp với regex
        return phoneNumber.matches("^\\d{10,15}$");
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}

