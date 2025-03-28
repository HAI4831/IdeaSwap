package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Optional<List<Conversation>> findByMemberIDsIn(List<String> memberIDs);
}
