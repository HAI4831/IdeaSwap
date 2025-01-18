package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.ICourses;
import nvh.run.ideaswap.data.dto.CoursesDTO;
import nvh.run.ideaswap.data.entity.Courses;
import nvh.run.ideaswap.data.repository.CoursesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CoursesService implements ICourses {
    CoursesRepository coursesRepository;

    @Override
    public ResponseEntity<Object> getAllCourses() {
        List<Courses> courses = coursesRepository.findAll();
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve courses successfully", "courses", courses)
        );
    }

    @Override
    public ResponseEntity<Object> getCourseById(String id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve course successfully", "course", course)
        );
    }

    @Override
    public ResponseEntity<Object> createCourse(CoursesDTO coursesDTO) {
        Courses course = coursesRepository.save(
                Courses.builder()
                        .userID(null) // Resolve Users entity reference in actual implementation
                        .categoryID(coursesDTO.getCategoryId())
                        .title(coursesDTO.getTitle())
                        .description(coursesDTO.getDescription())
                        .imageUrl(coursesDTO.getImageUrl())
                        .view(coursesDTO.getView())
                        .build()
        );
        return ResponseEntity.status(201).body(
                Map.of("success", true, "message", "Course created successfully", "course", course)
        );
    }

    @Override
    public ResponseEntity<Object> updateCourse(String id, CoursesDTO coursesDTO) {
        getCourseById(id);
        Courses updatedCourse = coursesRepository.save(
                Courses.builder()
                        .id(id)
                        .userID(null) // Resolve Users entity reference
                        .categoryID(coursesDTO.getCategoryId())
                        .title(coursesDTO.getTitle())
                        .description(coursesDTO.getDescription())
                        .imageUrl(coursesDTO.getImageUrl())
                        .view(coursesDTO.getView())
                        .build()
        );
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Course updated successfully", "course", updatedCourse)
        );
    }

    @Override
    public ResponseEntity<Object> deleteCourse(String id) {
        getCourseById(id);
        coursesRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Course deleted successfully"));
    }

    @Override
    public ResponseEntity<Object> incrementView(String id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setView(course.getView() + 1);
        coursesRepository.save(course);
        return ResponseEntity.ok(
                Map.of("success", true, "message", "View count updated successfully", "course", course)
        );
    }

    @Override
    public ResponseEntity<Object> searchCourses(String keyword) {
        List<Courses> courses = coursesRepository.findByTitleContainingIgnoreCase(keyword);
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Search results retrieved successfully", "courses", courses)
        );
    }
}
