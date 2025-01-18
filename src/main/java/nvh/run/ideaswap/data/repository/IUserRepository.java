package nvh.run.authsystemgradle.data.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import nvh.run.authsystemgradle.data.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);

    Optional<Boolean> existsByUsername(@NotBlank(message = "Tên người dùng không được để trống") String username);

    boolean existsByEmail(@Email(message = "Email không hợp lệ") String email);
}
