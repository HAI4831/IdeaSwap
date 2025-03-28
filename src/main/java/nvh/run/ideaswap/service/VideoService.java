package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.dto.UpdateViewVideoRequest;
import nvh.run.ideaswap.data.dto.VideoRequest;
import nvh.run.ideaswap.data.entity.*;
import nvh.run.ideaswap.data.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
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
public class VideoService {
    VideoRepository videoRepository;
    UserService userService;
    CoursesService coursesService;
    CloudinaryService cloudinaryService;
    NotificationService notificationService;
    CensorshipsService censorshipsService;

    @Autowired
    public VideoService(
            VideoRepository videoRepository,
            UserService userService,
            CoursesService coursesService,
            CloudinaryService cloudinaryService,
            NotificationService notificationService,
            @Lazy CensorshipsService censorshipsService) {
        this.videoRepository = videoRepository;
        this.userService = userService;
        this.coursesService = coursesService;
        this.cloudinaryService = cloudinaryService;
        this.notificationService = notificationService;
        this.censorshipsService = censorshipsService;
    }
//    @Cacheable(value = "users",key = "'page:' + #page + ':size:' + #size")
    public Page<Video> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Video> videosList;
        try {
            videosList = videoRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("find all video failed",e);
        }
        return videosList;
    }
//    @Cacheable(value="videos")
    public List<Video> getAll() {
        List<Video> videoList;
        try {
            videoList = videoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("find all video failed",e);
        }
        return videoList;
    }
    @Cacheable(value="video",key="#id",condition = "#id!=null")
    public Video getById(String id) {
        Video video;
        try {
            video = videoRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("find video failed with id:"+id,e);
        }
            return video;
    }
    @Cacheable(value="video",key="#videoRequest.id",condition = "#videoRequest.id!=null")
    public Video save(VideoRequest videoRequest) {
        Video video;
        User user = userService.getUserById(videoRequest.getUserID());
        Course course = coursesService.getCourseById(videoRequest.getCourseID());
        try {
            String imageUrl = cloudinaryService.uploadImage(videoRequest.getImageBase64(),null,"video");
//            if (imageUrl == null) {
//                throw new RuntimeException("Course image upload failed");
//            }
            video = videoRepository.save(
                    Video.builder()
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
        try {
           censorshipsService.createCensorship(
                   Censorship.builder()
                           .status(Status.pending)
                           .contentID(video.getId())
                           .feedback("Video is awaiting approval")
                   .build()
           );
        } catch (Exception e) {
            throw new RuntimeException("create censorship for video failed with ",e);
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

    @CachePut(value = "video", key = "#videoRequest.id", condition = "#videoRequest.id != null")
    public Video update(String id, VideoRequest videoRequest) {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        User user = Optional.ofNullable(videoRequest.getUserID())
                .map(userService::getUserById)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = Optional.ofNullable(videoRequest.getCourseID())
                .map(coursesService::getCourseById)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        String imageUrl = Optional.ofNullable(videoRequest.getImageBase64())
                .map(img -> cloudinaryService.uploadImage(img, null, "video"))
                .orElse(existingVideo.getImageUrl()); // Giữ nguyên ảnh cũ nếu không có ảnh mới

        Video updatedVideo = existingVideo.toBuilder()
                .userID(user.getId())
                .courseID(course.getId())
                .title(Optional.ofNullable(videoRequest.getTitle()).orElse(existingVideo.getTitle()))
                .description(Optional.ofNullable(videoRequest.getDescription()).orElse(existingVideo.getDescription()))
                .view(Optional.ofNullable(videoRequest.getView()).orElse(existingVideo.getView()))
                .videoUrl(Optional.ofNullable(videoRequest.getVideoUrl()).orElse(existingVideo.getVideoUrl()))
                .imageUrl(imageUrl)
                .updatedAt(LocalDateTime.now())
                .createdAt(existingVideo.getCreatedAt()) // Giữ nguyên thời gian tạo ban đầu
                .id(id)
                .build();

        return videoRepository.save(updatedVideo);
    }

//    @CachePut(value="video",key="#videoRequest.id",condition = "#videoRequest.id!=null")
//    public Video update(String id , VideoRequest videoRequest) {
//        Video updatedVideo;
//        User user = userService.getUserById(videoRequest.getUserID());
//        Course course = coursesService.getCourseById(videoRequest.getCourseID());
//        Video video = getById(id);
//        try {
//            String imageUrl = cloudinaryService.uploadImage(videoRequest.getImageBase64(),null,"video");
////            if (imageUrl == null) {
////                throw new RuntimeException("Course image upload failed");
////            }
//            updatedVideo = videoRepository.save(
//                    Video.builder()
//                            .userID(user.getId())
//                            .courseID(course.getId())
//                            .title(videoRequest.getTitle())
//                            .description(videoRequest.getDescription())
//                            .view(videoRequest.getView())
//                            .videoUrl(videoRequest.getVideoUrl())
//                            .imageUrl(imageUrl)
//                            .updatedAt(LocalDateTime.now())
//                            .createdAt(LocalDateTime.now())
//                            .id(id)
//                            .build()
//            );
//        } catch (Exception e) {
//            throw new RuntimeException("update video failed",e);
//        }
//        return updatedVideo;
//    }
    @CacheEvict(value="video",key="#id",condition = "#id!=null")
    public Video delete(String id) {
        Video video = getById(id);
        try {
            // Xóa ảnh từ Cloudinary (nếu có)
            Optional.ofNullable(video.getImageUrl())
                    .ifPresent(imageUrl -> cloudinaryService.deleteImage(imageUrl, null));

            videoRepository.delete(video);
        } catch (Exception e) {
            throw new RuntimeException("delete video failed",e);
        }
        return video;
    }
    public Video updateView(String videoId, UpdateViewVideoRequest request) {
        try {
            return videoRepository.findById(videoId)
                    .map(video -> {
                        video.setView(
                                video.getView()+request.view()
                        );
                        video.setUpdatedAt(LocalDateTime.now());
                        return videoRepository.save(video);
                    })
                    .orElseThrow(() -> new RuntimeException("Video not found with id: " + videoId));
        } catch (Exception e) {
            throw new RuntimeException("Update view video failed", e);
        }
    }

//    @CachePut(value="video",key="#id",condition = "#id!=null")
//    public Video updateView(String id, VideoRequest videoRequest) {
//        Video video = getById(id);
//        User user = userService.getUserById(videoRequest.getUserID());
//        Course course = coursesService.getCourseById(videoRequest.getCourseID());
//        try {
//            video = videoRepository.save(
//                    Video.builder()
//                            .userID(user.getId())
//                            .courseID(course.getId())
//                            .title(videoRequest.getTitle())
//                            .description(videoRequest.getDescription())
//                            .view(videoRequest.getView())
//                            .videoUrl(videoRequest.getVideoUrl())
//                            .imageUrl(video.getImageUrl())
//                            .updatedAt(LocalDateTime.now())
//                            .createdAt(LocalDateTime.now())
//                            .id(null)
//                            .build()
//            );
//        }
//        catch (Exception e) {
//            throw new RuntimeException("update view video failed",e);
//        }
//        return video;
//    }
}
