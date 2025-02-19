package nvh.run.ideaswap.data.repository;

import jakarta.validation.constraints.NotNull;
import nvh.run.ideaswap.data.entity.Hearts;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface HeartsRepository extends MongoRepository<Hearts, String> {
    List<Hearts> findByUserID(@NotNull(message = "Người dùng không được để trống") String userID);

    List<Hearts> findByReferenceID(String referenceID);
}
