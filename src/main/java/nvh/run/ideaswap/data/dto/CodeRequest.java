package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.validator.IsObjectID;

import java.time.LocalDateTime;
@Data
@Builder
public class CodeRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private String user;
    @Builder.Default
    private int code=0;
    private LocalDateTime codeExpiration;
    private String userEmail;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
