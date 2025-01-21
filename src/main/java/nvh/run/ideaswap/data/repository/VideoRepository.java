package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Videos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Videos,String> {
}
