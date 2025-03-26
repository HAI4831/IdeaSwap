package nvh.run.ideaswap.service;

//import com.cloudinary.*;
//import com.cloudinary.utils.ObjectUtils;
//import io.github.cdimascio.dotenv.Dotenv;
//
//import java.util.Map;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.CourseRequest;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.entity.Courses;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.CoursesRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    NotificationService notificationService;
    CloudinaryService cloudinaryService;

//    @Cacheable(value = "courses",key = "'page:' + #page + ':size:' + #size")
    public Page<Courses> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Courses> coursesPage;
        try {
            coursesPage = coursesRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Courses failed",e);
        }
        return coursesPage;
    }
//    @Cacheable(value="courses")
    public List<Courses> getAllCourses() {
        List<Courses> courseList;
        try {
            courseList = coursesRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Courses failed",e);
        }
        return courseList;
    }

    @Cacheable(value="course",key="#id",condition = "#id!=null")
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

    @Cacheable(value="course",key="#courseRequest.id",condition = "#courseRequest.id!=null")
    public Courses createCourse(CourseRequest courseRequest) {
        Users user = userService.getUserById(courseRequest.getUserID());
        Categories category = categoryService.getCategoryById(courseRequest.getCategoryID());
        Courses course;
        try {
            String imageUrl = cloudinaryService.uploadImage(courseRequest.getImageBase64(),null);
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
            course = coursesRepository.save(
                    Courses.builder()
                            .id(courseRequest.getId())
                            .userID(user.getId())
                            .categoryID(category.getId())
                            .title(courseRequest.getTitle())
                            .description(courseRequest.getDescription())
                            .imageUrl(imageUrl)
                            .view(courseRequest.getView())
                            .createdAt(courseRequest.getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the course",e);
        }
//        notificationService.createNotification(
//                NotificationRequest.builder()
//                        .description("A new course has just been created")
//                        .imageUrl(course.getImageUrl())
//                        .userIDs(null)
//                        .build()
//        );
        return course;
    }

    @Cacheable(value="course",key="#id",condition = "#id!=null")
    public Courses updateCourse(String id, CourseRequest courseRequest) {
        getCourseById(id);
        Users user = userService.getUserById(courseRequest.getUserID());
        Categories category = categoryService.getCategoryById(courseRequest.getCategoryID());
        Courses course;
        try {
            String imageUrl = cloudinaryService.uploadImage(courseRequest.getImageBase64(),null);
            if (imageUrl == null) {
                throw new RuntimeException("Course image upload failed");
            }
            course = coursesRepository.save(
                    Courses.builder()
                            .id(id)
                            .userID(user.getId())
                            .categoryID(category.getId())
                            .title(courseRequest.getTitle())
                            .description(courseRequest.getDescription())
                            .imageUrl(imageUrl)
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

    @CacheEvict(value="course",key="#id",condition = "#id!=null")
    public Courses deleteCourse(String id) {
        Courses courses = getCourseById(id);
        try {
            coursesRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the course",e);
        }
        return courses;
    }

    @Cacheable(value="course",key="#id",condition = "#id!=null")
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

    @Cacheable(value="course",key="#keyword",condition = "#keyword=null")
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
