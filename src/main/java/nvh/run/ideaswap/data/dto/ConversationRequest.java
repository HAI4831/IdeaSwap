package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ConversationRequest {
    private String id;
    @IsObjectID
    private List<String> members;
    @Builder.Default
    private String wallpaperUrl="";
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
