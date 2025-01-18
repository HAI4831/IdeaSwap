package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.CoursesDTO;
import org.springframework.http.ResponseEntity;

public interface ICourses {
    ResponseEntity<Object> getAllCourses();

    ResponseEntity<Object> getCourseById(String id);

    ResponseEntity<Object> createCourse(CoursesDTO coursesDTO);

    ResponseEntity<Object> updateCourse(String id, CoursesDTO coursesDTO);

    ResponseEntity<Object> deleteCourse(String id);

    ResponseEntity<Object> incrementView(String id);

    ResponseEntity<Object> searchCourses(String keyword);
}
