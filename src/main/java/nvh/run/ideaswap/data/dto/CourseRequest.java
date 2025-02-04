package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class CourseRequest {
    private String id;
    @IsObjectID
    private String userID;
    @IsObjectID
    private String categoryID;
    private String title;
    private String description;
    private MultipartFile imageBase64;
    private int view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
