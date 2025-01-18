package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.IUserService;
import nvh.run.ideaswap.common.exceptions.exception.custom.auth.UsernameAlreadyExistException;
import nvh.run.ideaswap.data.dto.UserDTO;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.IUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {

    IUserRepository iUserRepository;
    @Override
    public ResponseEntity<Object> getAllUsers() {
        List<Users> users = iUserRepository.findAll();
        return ResponseEntity.status(200).body(
                Map.of("success", true, "message", "Retrieve List Users successfully", "users", users)
        );
    }

    @Override
    public ResponseEntity<Object> getUserById(String id) {
        Users user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.status(200).body(
                Map.of("success", true, "message", "Retrieve User By ID successfully", "user", user)
        );
    }

    @Override
    public ResponseEntity<Object> createUser(UserDTO userDTO) {
        Users user = iUserRepository.save(
                Users.builder()
                        .firstName(userDTO.getFirstName())
                        .lastName(userDTO.getLastName())
                        .username(userDTO.getUsername())
                        .email(userDTO.getEmail())
                        .phoneNumber(userDTO.getPhoneNumber())
                        .address(userDTO.getAddress())
                        .avatar(userDTO.getAvatar())
                        .gender(userDTO.getGender())
                        .description(userDTO.getDescription())
                        .birthday(userDTO.getBirthday())
                        .build()
        );
        return ResponseEntity.status(201).body(
                Map.of("success", true, "message", "User created successfully", "user", user)
        );
    }

    @Override
    public ResponseEntity<Object> updateUser(String id, UserDTO userDTO) {
        getUserById(id);
        Users updatedUser = iUserRepository.save(
                Users.builder()
                        .id(id)
                        .firstName(userDTO.getFirstName())
                        .lastName(userDTO.getLastName())
                        .username(userDTO.getUsername())
                        .email(userDTO.getEmail())
                        .phoneNumber(userDTO.getPhoneNumber())
                        .address(userDTO.getAddress())
                        .avatar(userDTO.getAvatar())
                        .gender(userDTO.getGender())
                        .description(userDTO.getDescription())
                        .birthday(userDTO.getBirthday())
                        .build()
        );
        return ResponseEntity.status(200).body(
                Map.of("success", true, "message", "User updated successfully", "user", updatedUser)
        );
    }

    @Override
    public ResponseEntity<Object> deleteUser(String id) {
        getUserById(id);
        iUserRepository.deleteById(id);
        return ResponseEntity.status(200).body(
                Map.of("success", true, "message", "User deleted successfully")
        );
    }
//    ____________________________________
    @Override
    public Users findByUsername(String username) {
        return iUserRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with username:{}"+username));
    }

    @Override
    public Users findById(String id) {
        return iUserRepository.findById(id).orElseThrow(()->new RuntimeException("User not found with id:{}"+id));
    }

    @Override
    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username).orElseThrow(()->new UsernameAlreadyExistException("User not found with username:{}"+username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }
}
