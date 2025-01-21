package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    public Courses createCourse(Courses course) {
        try {
            course = coursesRepository.save(course);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the course",e);
        }
        return Courses.builder().build();
    }

    public Courses updateCourse(String id, Courses course) {
        getCourseById(id);
        course.setId(id);
        Courses updatedCourse ;
        try {
            updatedCourse = coursesRepository.save(course);
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
