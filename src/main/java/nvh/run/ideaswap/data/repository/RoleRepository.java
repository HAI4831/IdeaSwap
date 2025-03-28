package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}

