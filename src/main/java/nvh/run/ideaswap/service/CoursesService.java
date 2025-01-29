package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.CourseRequest;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.entity.Courses;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.CoursesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CoursesService {
    CoursesRepository coursesRepository;
    UserService userService;
    CategoryService categoryService;

    public List<Courses> getAllCourses() {
        List<Courses> courseList;
        try {
            courseList = coursesRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Courses failed",e);
        }
        return courseList;
    }

    public Courses getCourseById(String id) {
        Courses course;
        try {
            course = coursesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
        } catch (Exception e) {
            throw new RuntimeException("Course not found",e);
        }
        return course;
    }

    public Courses createCourse(CourseRequest courseRequest) {
        Users user = userService.getUserById(courseRequest.getUserID());
        Categories category = categoryService.getCategoryById(courseRequest.getCategoryID());
        Courses course;
        try {
            course = coursesRepository.save(
                    Courses.builder()
                            .id(courseRequest.getId())
                            .userID(user)
                            .categoryID(category)
                            .title(courseRequest.getTitle())
                            .description(courseRequest.getDescription())
                            .imageUrl(courseRequest.getImageUrl())
                            .view(courseRequest.getView())
                            .createdAt(courseRequest.getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the course",e);
        }
        return course;
    }

    public Courses updateCourse(String id, CourseRequest courseRequest) {
        getCourseById(id);
        Users user = userService.getUserById(courseRequest.getUserID());
        Categories category = categoryService.getCategoryById(courseRequest.getCategoryID());
        Courses course;
        try {
            course = coursesRepository.save(
                    Courses.builder()
                            .id(id)
                            .userID(user)
                            .categoryID(category)
                            .title(courseRequest.getTitle())
                            .description(courseRequest.getDescription())
                            .imageUrl(courseRequest.getImageUrl())
                            .view(courseRequest.getView())
                            .createdAt(courseRequest.getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the course",e);
        }
        return course;
    }

    public Courses deleteCourse(String id) {
        Courses courses = getCourseById(id);
        try {
            coursesRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the course",e);
        }
        return courses;
    }

    public Courses incrementView(String id) {
        Courses course ;
        try {
            course = coursesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the course",e);
        }
        course.setView(course.getView() + 1);
        coursesRepository.save(course);
        return course;
    }

    public List<Courses> searchCourses(String keyword) {
        List<Courses> courses ;
        try {
            courses = coursesRepository.findByTitleContainingIgnoreCase(keyword);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while searching the course",e);
        }
        return courses;
    }
}
