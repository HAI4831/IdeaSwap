package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private String userID;//user
    @IsObjectID
    private String moderatorID;//manager
    private String content;
    private String referenceID;
    private String type;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
