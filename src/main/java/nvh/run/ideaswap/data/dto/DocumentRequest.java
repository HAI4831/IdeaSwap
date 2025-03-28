package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.validator.IsObjectID;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentRequest {
    @IsObjectID
    private String userID;
    @IsObjectID
    private String categoryID;
    private String title;
    private String description;
    private MultipartFile file;
    private String imageBase64;
}
