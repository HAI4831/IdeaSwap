package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Categories;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Categories, String> {
}
