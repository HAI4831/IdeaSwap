package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.CourseRequest;
import nvh.run.ideaswap.service.CoursesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CoursesController {
    private final CoursesService coursesService;

    @GetMapping
    public ResponseEntity<Object> getAllCourses() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve courses successfully",
                        "courses", coursesService.getAllCourses()
                        )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCourseById(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve course successfully",
                        "course", coursesService.getCourseById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Course created successfully",
                        "course", coursesService.createCourse(courseRequest)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable String id, @Valid @RequestBody CourseRequest courseRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Course updated successfully",
                        "course", coursesService.updateCourse(id, courseRequest)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Course deleted successfully",
                "course", coursesService.deleteCourse(id)
        ));
    }

    @PatchMapping("/update/view/{id}")
    public ResponseEntity<Object> incrementView(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "View count updated successfully",
                        "course",coursesService.incrementView(id)
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchCourses(@RequestParam String keyword) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Search results retrieved successfully",
                        "courses", coursesService.searchCourses(keyword)
                )
        );
    }
}
