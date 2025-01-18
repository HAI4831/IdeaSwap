package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Managers;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ManagerRepository extends MongoRepository<Managers, String> {
    Optional<Managers> findByUsername(String username);
}

