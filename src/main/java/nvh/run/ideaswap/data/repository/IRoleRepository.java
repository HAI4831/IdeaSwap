package nvh.run.authsystemgradle.data.repository;

import nvh.run.authsystemgradle.data.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IRoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}
