package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CensorshipsDTO {
    @IsObjectID
    private String id;
    @IsObjectID
    private String contentID;
//    @NotBlank(message = "Trạng thái không được để trống")
//    @Size(max = 10, message = "Trạng thái không được quá 10 ký tự")
    private Status status;

    @NotBlank(message = "Phản hồi không được để trống")
    @Size(max = 500, message = "Phản hồi không được quá 500 ký tự")
    private String feedback;
}
