package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findByTitleContainingIgnoreCase(String keyword);
}
