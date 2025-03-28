package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.validator.IsObjectID;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private String userID;
    @IsObjectID
    private String categoryID;
    private String title;
    private String description;
    private String imageBase64;
//    private MultipartFile imageBase64;
    private int view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
