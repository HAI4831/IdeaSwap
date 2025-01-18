package nvh.run.ideaswap.data.dto;

import nvh.run.ideaswap.data.entity.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeartDTO {
    private String id;

    @NotNull(message = "ID người dùng không được để trống")
    private Users userID;

    @NotBlank(message = "ID tham chiếu không được để trống")
    private String referenceID;
}
