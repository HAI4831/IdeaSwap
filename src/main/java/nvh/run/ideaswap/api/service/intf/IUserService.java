package nvh.run.ideaswap.api.service.intf;


import nvh.run.ideaswap.data.dto.UserDTO;
import nvh.run.ideaswap.data.entity.Users;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<Object> getAllUsers();
    ResponseEntity<Object> getUserById(String id);
    ResponseEntity<Object> createUser(UserDTO userDTO);
    ResponseEntity<Object> updateUser(String id, UserDTO userDTO);
    ResponseEntity<Object> deleteUser(String id);
    Users findByUsername(String username);
    Users findById(String id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
