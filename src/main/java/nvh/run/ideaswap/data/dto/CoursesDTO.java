package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoursesDTO {
    @IsObjectID
    private String id;

    @IsObjectID
    @NotBlank(message = "Courses tham chiếu tới ID người dùng không được để trống")
    private String userId;

    @IsObjectID
    @NotBlank(message = "ID danh mục không được để trống")
    private String categoryId;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 100, message = "Tiêu đề không được quá 100 ký tự")
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 5000, message = "Mô tả không được quá 5000 ký tự")
    private String description;

    @NotBlank(message = "imageUrl không được để trống")
    @Size(max = 150, message = "imageUrl không được quá 150 ký tự")
    private String imageUrl;

    private int view;
}
