package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.CoursesDTO;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.entity.Courses;
import nvh.run.ideaswap.data.repository.CategoryRepository;
import nvh.run.ideaswap.data.repository.CoursesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CoursesService {
    CoursesRepository coursesRepository;
    CategoryRepository categoryRepository;

    public CoursesDTO getAllCourses() {
        List<Courses> courses = coursesRepository.findAll();
        return CoursesDTO.builder().build();
    }

    public CoursesDTO getCourseById(String id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return CoursesDTO.builder().build();
    }

    public CoursesDTO createCourse(CoursesDTO coursesDTO) {
        Categories category = categoryRepository.findById(coursesDTO.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));
        Courses course = coursesRepository.save(
                Courses.builder()
                        .userID(null) // Resolve Users entity reference in actual implementation
                        .categoryID(category)
                        .title(coursesDTO.getTitle())
                        .description(coursesDTO.getDescription())
                        .imageUrl(coursesDTO.getImageUrl())
                        .view(coursesDTO.getView())
                        .build()
        );
        return CoursesDTO.builder().build();
    }

    public CoursesDTO updateCourse(String id, CoursesDTO coursesDTO) {
        getCourseById(id);
        Categories category = categoryRepository.findById(coursesDTO.getCategoryId()).orElseThrow(()->new RuntimeException("Category not found"));
        Courses updatedCourse = coursesRepository.save(
                Courses.builder()
                        .id(id)
                        .userID(null) // Resolve Users entity reference
                        .categoryID(category)
                        .title(coursesDTO.getTitle())
                        .description(coursesDTO.getDescription())
                        .imageUrl(coursesDTO.getImageUrl())
                        .view(coursesDTO.getView())
                        .build()
        );
        return CoursesDTO.builder().build();
    }

    public CoursesDTO deleteCourse(String id) {
        getCourseById(id);
        coursesRepository.deleteById(id);
        return CoursesDTO.builder().build();
    }

    public CoursesDTO incrementView(String id) {
        Courses course = coursesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setView(course.getView() + 1);
        coursesRepository.save(course);
        return CoursesDTO.builder().build();
    }

    public CoursesDTO searchCourses(String keyword) {
        List<Courses> courses = coursesRepository.findByTitleContainingIgnoreCase(keyword);
        return CoursesDTO.builder().build();
    }
}
