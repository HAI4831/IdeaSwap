package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Messages;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Messages, ObjectId> {
    List<Messages> findByConversationID(String conversationID);
}

