package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.data.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationsRepository extends MongoRepository<Conversations, String> {
    Optional<Conversations> findByMembersIn(List<Users> members);
}
