package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.ReferenceTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NotificationRequest {
    private String id;
    @IsObjectID
    private List<String> userIDs;
    private String description;
    private String imageUrl;
    private boolean isUnRead=true;
    private String actorID;
    private ReferenceTypeEnum referenceType;
    private boolean isModal;
    private String referenceID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
