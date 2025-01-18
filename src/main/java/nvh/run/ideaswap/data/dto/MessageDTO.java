package nvh.run.ideaswap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagesDTO {
    private String id;
    private String senderId;
    private String content;
    private String messageParentId;
    private String conversationId;
    private String fileUrl;
    private String type;
}

