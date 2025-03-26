package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.dto.VideoRequest;
import nvh.run.ideaswap.data.entity.Courses;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.entity.Videos;
import nvh.run.ideaswap.data.repository.VideoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
public class VideoService {
    VideoRepository videoRepository;
    UserService userService;
    CoursesService coursesService;
    private final CloudinaryService cloudinaryService;
    private final NotificationService notificationService;

//    @Cacheable(value = "users",key = "'page:' + #page + ':size:' + #size")
    public Page<Videos> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Videos> videosList;
        try {
            videosList = videoRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("find all video failed",e);
        }
        return videosList;
    }
//    @Cacheable(value="videos")
    public List<Videos> getAll() {
        List<Videos> videosList;
        try {
            videosList = videoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("find all video failed",e);
        }
        return videosList;
    }
    @Cacheable(value="video",key="#id",condition = "#id!=null")
    public Videos getById(String id) {
        Videos video;
        try {
            video = videoRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("find video failed with id:"+id,e);
        }
        return video;
    }
    @Cacheable(value="video",key="#videoRequest.id",condition = "#videoRequest.id!=null")
    public Videos save(VideoRequest videoRequest) {
        Videos video;
        Users user = userService.getUserById(videoRequest.getUserID());
        Courses course = coursesService.getCourseById(videoRequest.getCourseID());
        try {
            String imageUrl = cloudinaryService.uploadImage(videoRequest.getImageUrl(),null);
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
            video = videoRepository.save(
                    Videos.builder()
                            .userID(user.getId())
                            .courseID(course.getId())
                            .title(videoRequest.getTitle())
                            .description(videoRequest.getDescription())
                            .view(videoRequest.getView())
                            .videoUrl(videoRequest.getVideoUrl())
                            .imageUrl(imageUrl)
                            .updatedAt(LocalDateTime.now())
                            .createdAt(LocalDateTime.now())
                            .id(videoRequest.getId())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("save video failed",e);
        }
        notificationService.createNotification(
                NotificationRequest.builder()
                        .description("Video is awaiting approval")
                        .imageUrl(video.getImageUrl())
                        .userIDs(List.of(user.getId()))
                        .build()
        );
        return video;
    }
    @CachePut(value="video",key="#videoRequest.id",condition = "#videoRequest.id!=null")
    public Videos update(String id ,VideoRequest videoRequest) {
        Videos updatedVideo;
        Users user = userService.getUserById(videoRequest.getUserID());
        Courses course = coursesService.getCourseById(videoRequest.getCourseID());
        Videos video = getById(id);
        try {
            String imageUrl = cloudinaryService.uploadImage(videoRequest.getImageUrl(),null);
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
            updatedVideo = videoRepository.save(
                    Videos.builder()
                            .userID(user.getId())
                            .courseID(course.getId())
                            .title(videoRequest.getTitle())
                            .description(videoRequest.getDescription())
                            .view(videoRequest.getView())
                            .videoUrl(videoRequest.getVideoUrl())
                            .imageUrl(imageUrl)
                            .updatedAt(LocalDateTime.now())
                            .createdAt(LocalDateTime.now())
                            .id(id)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("update video failed",e);
        }
        return updatedVideo;
    }
    @CacheEvict(value="video",key="#id",condition = "#id!=null")
    public Videos delete(String id) {
        Videos video = getById(id);
        try {
            videoRepository.delete(video);
        } catch (Exception e) {
            throw new RuntimeException("delete video failed",e);
        }
        return video;
    }
    @CachePut(value="video",key="#id",condition = "#id!=null")
    public Videos updateView(String id,VideoRequest videoRequest) {
        Videos video = getById(id);
        Users user = userService.getUserById(videoRequest.getUserID());
        Courses course = coursesService.getCourseById(videoRequest.getCourseID());
        try {
            video = videoRepository.save(
                    Videos.builder()
                            .userID(user.getId())
                            .courseID(course.getId())
                            .title(videoRequest.getTitle())
                            .description(videoRequest.getDescription())
                            .view(videoRequest.getView())
                            .videoUrl(videoRequest.getVideoUrl())
                            .imageUrl(video.getImageUrl())
                            .updatedAt(LocalDateTime.now())
                            .createdAt(LocalDateTime.now())
                            .id(null)
                            .build()
            );
        }
        catch (Exception e) {
            throw new RuntimeException("update view video failed",e);
        }
        return video;
    }
}
