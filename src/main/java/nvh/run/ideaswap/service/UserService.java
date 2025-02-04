package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.exceptions.exception.custom.auth.UsernameAlreadyExistException;
import nvh.run.ideaswap.data.dto.UserRequest;
import nvh.run.ideaswap.data.entity.Roles;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.IUserRepository;
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
    private final CloudinaryService cloudinaryService;

    public List<Users> getAllUsers() {
        List<Users> users ;
        try {
            users = iUserRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Users failed",e);
        }
        return users;
    }

    public Users getUserById(String id) {
        Users user ;
        try {
            user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve User By ID failed",e);
        }
        return user;
    }
    public Users createUser(UserRequest userRequest) {
        Users user;
        try {
            String imageUrl = cloudinaryService.uploadImage(userRequest.getAvatar());
            if (imageUrl == null) {
                throw new RuntimeException("Course image upload failed");
            }
            if(existsByEmail(userRequest.getEmail())) throw new RuntimeException("Email already exist");
            if(existsByPhoneNumber(userRequest.getPhoneNumber())) throw new RuntimeException("Phone number already exist");
            if(existsByUsername(userRequest.getUsername())) throw new RuntimeException("Username already exist");
            Roles role = roleService.getRoleById(userRequest.getRoleID());
            user = iUserRepository.save(
                    Users.builder()
                            .id(userRequest.getId())
                            .roleID(role)
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
            throw new RuntimeException("Create User failed",e);
        }
        return user;
    }
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
                            .roleID(role)
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

    public Users deleteUser(String id) {
       Users user= getUserById(id);
        try {
            iUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete User failed",e);
        }
        return user;
    }
//    ____________________________________
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

    public Users findById(String id) {
        Users user ;
        try {
            user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve User By ID failed",e);
        }
        return user;
    }
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
}
