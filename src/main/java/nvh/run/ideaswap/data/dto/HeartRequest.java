package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.validator.IsObjectID;

@Data
@Builder
public class HeartRequest {
    @IsObjectID
    private String userID;
    private String referenceID;
}
