package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Censorship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CensorshipRepository extends MongoRepository<Censorship, String> {
    Censorship findCensorshipsByContentID(String contentID);
}
