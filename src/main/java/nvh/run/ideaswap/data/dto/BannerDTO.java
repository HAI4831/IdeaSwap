package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BannerDTO {
    @IsObjectID
    private String id;
    @IsObjectID
    private String managerID;
    @JsonProperty("name")
    @NotBlank(message = "Tên banner không được để trống")
    @Size(max = 30, message = "Tên banner không được quá 30 ký tự")
    private String name;

    @Field("site")
    @NotBlank(message = "URL không được để trống")
    @Size(max = 30, message = "Site không được quá 30 ký tự")
    private String site;

    @Field("imageUrl")
    @NotBlank(message = "URL hình ảnh không được để trống")
    @Size(max = 150, message = "URL hình ảnh không được quá 150 ký tự")
    private String imageUrl;
}
