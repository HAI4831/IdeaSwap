package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video,String> {
}
