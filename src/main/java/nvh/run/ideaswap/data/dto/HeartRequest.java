package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;

@Data
@Builder
public class HeartRequest {
    private String id;
    @IsObjectID
    private String userID;
    private String referenceID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
