package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDTO {
    @IsObjectID
    private String id;
    private String content;
    @IsObjectID
    private String referenceId;
    @IsObjectID
    private String userId;
    private String type;
    private Status status;
    @IsObjectID
    private String moderatorId;
}

