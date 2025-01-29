package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Managers;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IManagerRepository extends MongoRepository<Managers, String> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUsername(String username);
}

