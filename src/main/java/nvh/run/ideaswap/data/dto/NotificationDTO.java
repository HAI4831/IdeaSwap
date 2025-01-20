package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.ReferenceTypeEnum;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTO {
    @IsObjectID
    private String id;
    private String description;
    private String imageUrl;
    private boolean isUnRead;
    @IsObjectID
    private List<String> userIds;
    private String actorId;
    private ReferenceTypeEnum referenceType;
    private boolean isModal;
    @IsObjectID
    private String referenceId;
}

