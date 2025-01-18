package nvh.run.ideaswap.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDTO {
    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 5000, message = "Nội dung không được quá 5000 ký tự")
    private String content;
    @Size(max = 1000, message = "URL không được quá 1000 ký tự")
    @Field(name="imageBase64")
    private String url;
    private String userID;
//    private Users userID;
    @Field(name="categoryID")
    private String categoryID;
//    private Categories categoryID;
}

