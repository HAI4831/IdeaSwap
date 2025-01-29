package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.time.LocalDateTime;

@Data
@Builder
public class BannerRequest {
    private String id;
    @IsObjectID
    private String managerID;
    private String name;
    private String site;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
