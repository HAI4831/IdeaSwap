package nvh.run.ideaswap.data.repository;

import jakarta.validation.constraints.NotNull;
import nvh.run.ideaswap.data.entity.Heart;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface HeartRepository extends MongoRepository<Heart, String> {
    List<Heart> findByUserID(@NotNull(message = "Người dùng không được để trống") String userID);

    List<Heart> findByReferenceID(String referenceID);
}
