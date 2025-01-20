package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class HeartDTO {
    @IsObjectID
    private String id;
    @IsObjectID
    @NotNull(message = "ID người dùng không được để trống")
    private String userID;
    @IsObjectID
    @NotBlank(message = "ID tham chiếu không được để trống")
    private String referenceID;
}
