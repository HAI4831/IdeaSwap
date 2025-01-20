package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nvh.run.ideaswap.common.validator.IsObjectID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentsDTO {
    @IsObjectID
    private String id;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 1000, message = "Nội dung không được quá 1000 ký tự")
    private String content;

    @IsObjectID
    private String parentCommentID;

    @IsObjectID
    @NotBlank(message = "ID người dùng không được để trống")
    private String userID;

    @IsObjectID
    private String referenceID;
}
