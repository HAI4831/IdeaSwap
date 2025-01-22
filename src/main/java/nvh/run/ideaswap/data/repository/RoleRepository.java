package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Roles;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Roles, ObjectId> {
    Optional<Roles> findByName(String name);
}

