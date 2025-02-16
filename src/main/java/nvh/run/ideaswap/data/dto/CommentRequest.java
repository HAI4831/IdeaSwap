package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    private String content;
    @IsObjectID
    private String parentCommentID;
    @IsObjectID
    private String userID;
    @IsObjectID
    private String referenceID;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
