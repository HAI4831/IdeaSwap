package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Courses;
import nvh.run.ideaswap.service.CoursesService;
import org.bson.types.ObjectId;
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
    public ResponseEntity<Object> getCourseById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve course successfully",
                        "course", coursesService.getCourseById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@Valid @RequestBody Courses course) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Course created successfully",
                        "course", coursesService.createCourse(course)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable ObjectId id, @Valid @RequestBody Courses course) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Course updated successfully",
                        "course", coursesService.updateCourse(id, course)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable ObjectId id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Course deleted successfully",
                "course", coursesService.deleteCourse(id)
        ));
    }

    @PatchMapping("/update/view/{id}")
    public ResponseEntity<Object> incrementView(@PathVariable ObjectId id) {
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
