package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ConversationRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private List<String> members;
    @Builder.Default
    private String wallpaperUrl="";
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
