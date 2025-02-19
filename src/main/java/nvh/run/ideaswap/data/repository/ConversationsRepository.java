package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Conversations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationsRepository extends MongoRepository<Conversations, String> {
    Optional<List<Conversations>> findByMemberIDsIn(List<String> memberIDs);
}
