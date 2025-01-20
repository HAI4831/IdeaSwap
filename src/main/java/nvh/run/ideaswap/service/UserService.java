package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.exceptions.exception.custom.auth.UsernameAlreadyExistException;
import nvh.run.ideaswap.data.dto.UserDTO;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.IUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    IUserRepository iUserRepository;
    public UserDTO getAllUsers() {
        List<Users> users = iUserRepository.findAll();
        return UserDTO.builder().build();
    }

    public UserDTO getUserById(String id) {
        Users user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.builder().build();
    }
    public UserDTO createUser(UserDTO userDTO) {
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
        return UserDTO.builder().build();
    }
    public UserDTO updateUser(String id, UserDTO userDTO) {
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
        return UserDTO.builder().build();
    }

    public UserDTO deleteUser(String id) {
        getUserById(id);
        iUserRepository.deleteById(id);
        return UserDTO.builder().build();
    }
//    ____________________________________
    public Users findByUsername(String username) {
        return iUserRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with username:{}"+username));
    }

    public UserDTO findById(String id) {
        Users user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.builder().build();
    }
    public UserDTO findByPhoneNumber(String phoneNumber){
        Users user = iUserRepository.findByPhone(phoneNumber).orElseThrow(()->new RuntimeException("User not found with phoneNumber:{}"+phoneNumber));
        return UserDTO.builder().build();
    }
    public boolean existsByPhoneNumber(String phoneNumber) {
        return findByPhoneNumber(phoneNumber)!=null;
    }


    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username).orElseThrow(()->new UsernameAlreadyExistException("User not found with username:{}"+username));
    }


    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }
}
