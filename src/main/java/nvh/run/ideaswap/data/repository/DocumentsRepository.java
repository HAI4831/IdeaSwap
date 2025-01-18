package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Documents;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DocumentsRepository extends MongoRepository<Documents, String> {
    @Query("{'$or': [{'title': {$regex: ?0, $options: 'i'}}, {'description': {$regex: ?0, $options: 'i'}}]}")
    List<Documents> searchByTitleOrDescription(String keyword);
    List<Documents> findByTitleContainingIgnoreCase(String keyword);
}
