package nvh.run.ideaswap.data.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nvh.run.ideaswap.data.entity.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IManagerRepository extends MongoRepository<Manager, String> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUsername(String username);

    Optional<Manager> findByUsername(@NotBlank(message = "Tên đăng nhập không được để trống") @Size(max = 50 , message = "username không quá 50 kí tự") String username);
}

