package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IRoleRepository extends MongoRepository<Roles, String> {
    Optional<Roles> findByName(String name);
}
