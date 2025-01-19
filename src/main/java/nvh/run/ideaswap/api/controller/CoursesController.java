package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.intf.ICourses;
import nvh.run.ideaswap.data.dto.CoursesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CoursesController {
    private final ICourses coursesService;

    @GetMapping
    public ResponseEntity<Object> getAllCourses() {
        return coursesService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCourseById(@PathVariable String id) {
        return coursesService.getCourseById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createCourse(@Valid @RequestBody CoursesDTO coursesDTO) {
        return coursesService.createCourse(coursesDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourse(@PathVariable String id, @Valid @RequestBody CoursesDTO coursesDTO) {
        return coursesService.updateCourse(id, coursesDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable String id) {
        return coursesService.deleteCourse(id);
    }

    @PatchMapping("/update/view/{id}")
    public ResponseEntity<Object> incrementView(@PathVariable String id) {
        return coursesService.incrementView(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchCourses(@RequestParam String keyword) {
        return coursesService.searchCourses(keyword);
    }
}
