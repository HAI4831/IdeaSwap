package nvh.run.ideaswap.data.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import nvh.run.ideaswap.data.entity.Users;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<Users, String> {
    Optional<Users> findByUsernameOrEmail(String username, String email);
    @NotNull Optional<Users> findById(@NotNull String id);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByPhoneNumber(String phone);

    Optional<Boolean> existsByUsername(@NotBlank(message = "Tên người dùng không được để trống") String username);

    boolean existsByEmail(@Email(message = "Email không hợp lệ") String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
