package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;
@Data
@Builder
public class MessageRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private String senderID;
    @IsObjectID
    private String conversationID;
    private String content;
    private String messageParentID;
    private String fileUrl;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
