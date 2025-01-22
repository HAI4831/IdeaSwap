package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Hearts;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface HeartsRepository extends MongoRepository<Hearts, ObjectId> {
    List<Hearts> findByUserID(ObjectId userID);

    List<Hearts> findByReferenceID(ObjectId referenceID);
}
