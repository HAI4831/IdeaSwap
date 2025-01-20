package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogDTO {
    @IsObjectID
    private String id;
    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 5000, message = "Nội dung không được quá 5000 ký tự")
    private String content;
    @Size(max = 1000, message = "URL không được quá 1000 ký tự")
    @Field(name="imageBase64")
    private String url;
    @IsObjectID
    private String userID;
//    private Users userID;
    @IsObjectID
    @Field(name="categoryID")
    private String categoryID;
//    private Categories categoryID;
}

