package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConversationID(String conversationID);
}

