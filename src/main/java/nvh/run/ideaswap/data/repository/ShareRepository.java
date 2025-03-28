package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Share;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShareRepository extends MongoRepository<Share,String> {
}
