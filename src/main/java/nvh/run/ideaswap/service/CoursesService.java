package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.data.dto.CourseRequest;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.entity.Course;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.CourseRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class CoursesService {
    CourseRepository courseRepository;
    UserService userService;
    CategoryService categoryService;
    NotificationService notificationService;
    CloudinaryService cloudinaryService;

//    @Cacheable(value = "courses",key = "'page:' + #page + ':size:' + #size")
    public Page<Course> getAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursesPage;
        try {
            coursesPage = courseRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Courses failed",e);
        }
        return coursesPage;
    }
//    @Cacheable(value="courses")
    public List<Course> getAllCourses() {
        List<Course> courseList;
        try {
            courseList = courseRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Courses failed",e);
        }
        return courseList;
    }

    @Cacheable(value="course",key="#id",condition = "#id!=null")
    public Course getCourseById(String id) {
        Course course;
        try {
            course = courseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
        } catch (Exception e) {
            throw new RuntimeException("Course not found",e);
        }
        return course;
    }

    @Cacheable(value="course",key="#courseRequest.id",condition = "#courseRequest.id!=null")
    public Course createCourse(CourseRequest courseRequest) {
        User user = userService.getUserById(courseRequest.getUserID());
//        Category category = categoryService.getCategoryById(courseRequest.getCategoryID());
        Course course;
        try {
            String imageUrl = cloudinaryService.uploadImage(courseRequest.getImageBase64(),null,"course");
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
            course = courseRepository.save(
                    Course.builder()
                            .id(courseRequest.getId())
                            .userID(user.getId())
//                            .categoryID(category.getId())
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
        notificationService.createNotification(
                NotificationRequest.builder()
                        .description("A new course has just been created")
                        .imageUrl(course.getImageUrl())
                        .userIDs(List.of(user.getId()))
                        .build()
        );
        return course;
    }
    @Cacheable(value = "course", key = "#id", condition = "#id != null")
    public Course updateCourse(String id, CourseRequest courseRequest) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        User user = Optional.ofNullable(courseRequest.getUserID())
                .map(userService::getUserById)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        Category category = Optional.ofNullable(courseRequest.getCategoryID())
//                .map(categoryService::getCategoryById)
//                .orElseThrow(() -> new RuntimeException("Category not found"));

        String imageUrl = Optional.ofNullable(courseRequest.getImageBase64())
                .map(img -> cloudinaryService.uploadImage(img, null, "course"))
                .orElse(existingCourse.getImageUrl()); // Giữ nguyên ảnh cũ nếu không có ảnh mới

        Course updatedCourse = existingCourse.toBuilder()
                .userID(user.getId())
//                .categoryID(category.getId())
                .title(Optional.ofNullable(courseRequest.getTitle()).orElse(existingCourse.getTitle()))
                .description(Optional.ofNullable(courseRequest.getDescription()).orElse(existingCourse.getDescription()))
                .imageUrl(imageUrl)
                .view(Optional.ofNullable(courseRequest.getView()).orElse(existingCourse.getView()))
                .createdAt(Optional.ofNullable(courseRequest.getCreatedAt()).orElse(existingCourse.getCreatedAt()))
                .updatedAt(LocalDateTime.now())
                .build();

        return courseRepository.save(updatedCourse);
    }


//    @Cacheable(value="course",key="#id",condition = "#id!=null")
//    public Course updateCourse(String id, CourseRequest courseRequest) {
//        getCourseById(id);
//        User user = userService.getUserById(courseRequest.getUserID());
//        Category category = categoryService.getCategoryById(courseRequest.getCategoryID());
//        Course course;
//        try {
//            String imageUrl = cloudinaryService.uploadImage(courseRequest.getImageBase64(),null,"course");
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
//            course = courseRepository.save(
//                    Course.builder()
//                            .id(id)
//                            .userID(user.getId())
//                            .categoryID(category.getId())
//                            .title(courseRequest.getTitle())
//                            .description(courseRequest.getDescription())
//                            .imageUrl(imageUrl)
//                            .view(courseRequest.getView())
//                            .createdAt(courseRequest.getCreatedAt())
//                            .updatedAt(LocalDateTime.now())
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("An error occurred while updating the course",e);
//        }
//        return course;
//    }

    @CacheEvict(value="course",key="#id",condition = "#id!=null")
    public Course deleteCourse(String id) {
        Course course = getCourseById(id);
        try {
            // Xóa ảnh từ Cloudinary (nếu có)
            Optional.ofNullable(course.getImageUrl())
                    .ifPresent(imageUrl -> cloudinaryService.deleteImage(imageUrl, null));
        } catch (Exception e) {
            log.warn("delete image failed");
        }
        try {
            courseRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the course",e);
        }
        return course;
    }

    @Cacheable(value="course",key="#id",condition = "#id!=null")
    public Course incrementView(String id) {
        Course course ;
        try {
            course = courseRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Course not found"));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the course",e);
        }
        course.setView(course.getView() + 1);
        courseRepository.save(course);
        return course;
    }

    @Cacheable(value="course",key="#keyword",condition = "#keyword=null")
    public List<Course> searchCourses(String keyword) {
        List<Course> cours;
        try {
            cours = courseRepository.findByTitleContainingIgnoreCase(keyword);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while searching the course",e);
        }
        return cours;
    }
}
