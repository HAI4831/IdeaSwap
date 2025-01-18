package nvh.run.ideaswap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private String id;
    private String description;
    private String imageUrl;
    private boolean isUnRead;
    private List<String> userIds;
    private String actorId;
    private String referenceType;
    private boolean isModal;
    private String referenceId;
}

