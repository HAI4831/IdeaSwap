package nvh.run.ideaswap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportsDTO {
    private String id;
    private String content;
    private String referenceId;
    private String userId;
    private String type;
    private String status;
    private String moderatorId;
}

