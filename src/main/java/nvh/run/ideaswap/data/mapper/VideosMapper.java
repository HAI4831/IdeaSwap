package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.VideosDto;
import nvh.run.ideaswap.data.entity.Courses;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.entity.Videos;

public class VideosMapper {
    public static VideosDto toDto(Videos videos) {
        if (videos == null) return null;
        return VideosDto.builder()
                .id(videos.getId())
                .userID(videos.getUserID() != null ? videos.getUserID().getId() : null)
                .title(videos.getTitle())
                .description(videos.getDescription())
                .imageUrl(videos.getImageUrl())
                .videoUrl(videos.getVideoUrl())
                .view(videos.getView())
                .courseID(videos.getCourseID() != null ? videos.getCourseID().getId() : null)
                .build();
    }

    public static Videos toEntity(VideosDto videosDto, Users user, Courses course) {
        if (videosDto == null) return null;
        return Videos.builder()
                .id(videosDto.getId())
                .userID(user)
                .title(videosDto.getTitle())
                .description(videosDto.getDescription())
                .imageUrl(videosDto.getImageUrl())
                .videoUrl(videosDto.getVideoUrl())
                .view(videosDto.getView())
                .courseID(course)
                .build();
    }
}
