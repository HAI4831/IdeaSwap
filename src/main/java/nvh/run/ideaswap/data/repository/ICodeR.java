package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Codes;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICodeR extends MongoRepository<Codes, ObjectId> {
}
