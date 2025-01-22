package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Shares;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShareRepository extends MongoRepository<Shares, ObjectId> {
}
