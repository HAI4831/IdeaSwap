package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.ReferenceTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NotificationRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private List<String> userIDs;
    private String description;
    private String imageUrl;
    @Builder.Default
    private boolean isUnRead=true;
    private String actorID;
    private ReferenceTypeEnum referenceType;
    private boolean isModal;
    private String referenceID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
