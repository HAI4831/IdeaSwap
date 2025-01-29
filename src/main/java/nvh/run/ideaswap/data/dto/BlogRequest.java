package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogRequest {
    private String id;
    @IsObjectID
    private String userID;
    @IsObjectID
    private String categoryID;
    private String content;
    private String url;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
