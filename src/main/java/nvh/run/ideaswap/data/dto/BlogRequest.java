package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.validator.IsObjectID;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private String userID;
//    @IsObjectID
//    private String categoryID;
    private String content;
    private String imageBase64;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
