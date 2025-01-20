package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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
public class FollowDTO {
    @IsObjectID
    private String id;
    @IsObjectID
    @NotBlank(message = "ID người theo dõi không được để trống")
    private String followerID;
    @IsObjectID
    @NotBlank(message = "ID người dùng không được để trống")
    private String userID;
}
