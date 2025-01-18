package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessagesRepository extends MongoRepository<Messages, String> {
    List<Messages> findByConversationId(String conversationId);
}

