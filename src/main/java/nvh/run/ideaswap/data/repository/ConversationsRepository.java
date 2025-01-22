package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Conversations;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationsRepository extends MongoRepository<Conversations, ObjectId> {
}
