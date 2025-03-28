package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DocumentRepository extends MongoRepository<Document, String> {
    @Query("{'$or': [{'title': {$regex: ?0, $options: 'i'}}, {'description': {$regex: ?0, $options: 'i'}}]}")
    List<Document> searchByTitleOrDescription(String keyword);
    List<Document> findByTitleContainingIgnoreCase(String keyword);
}
