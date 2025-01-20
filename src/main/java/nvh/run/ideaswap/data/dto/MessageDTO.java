package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDTO {
    @IsObjectID
    private String id;
    @IsObjectID
    private String senderId;
    private String content;
    @IsObjectID
    private String messageParentId;
    @IsObjectID
    private String conversationId;
    private String fileUrl;
    private String type;
}

