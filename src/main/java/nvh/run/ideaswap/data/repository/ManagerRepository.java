package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Manager;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ManagerRepository extends MongoRepository<Manager, String> {
    Optional<Manager> findByUsername(String username);
    @NotNull Optional<Manager> findById(@NotNull String id);
}

