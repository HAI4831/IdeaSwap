package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private String userID;
    @IsObjectID
    private String courseID;//course
    private String title;
    private String description;
    private String imageUrl;//base64
//    private MultipartFile imageUrl;
    private String videoUrl;
    private int view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
