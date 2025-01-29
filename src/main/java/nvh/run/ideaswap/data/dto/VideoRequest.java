package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;
@Data
@Builder
public class VideoRequest {
    private String id;
    @IsObjectID
    private String userID;
    @IsObjectID
    private String courseID;//course
    private String title;
    private String description;
    private String imageUrl;
    private String videoUrl;
    private int view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
