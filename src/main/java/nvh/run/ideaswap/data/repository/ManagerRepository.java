package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Managers;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ManagerRepository extends MongoRepository<Managers, ObjectId> {
    Optional<Managers> findByUsername(String username);
    @NotNull Optional<Managers> findById(@NotNull String id);
}

