package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;
@Data
@Builder
public class CodeRequest {
    private String id;
    @IsObjectID
    private String user;
    private int code=0;
    private LocalDateTime codeExpiration;
    private String userEmail;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
