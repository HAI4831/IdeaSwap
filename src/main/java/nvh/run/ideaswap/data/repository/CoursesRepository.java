package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Courses;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends MongoRepository<Courses, String> {
    List<Courses> findByTitleContainingIgnoreCase(String keyword);
}
