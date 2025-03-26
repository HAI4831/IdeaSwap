package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;

@Data
@Builder
public class BannerRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @IsObjectID
    private String managerID;
    private String name;
    private String site;
    private String imageBase64;
//    private MultipartFile imageBase64;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
