package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Blogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BlogRepository extends MongoRepository<Blogs, String> {
}

