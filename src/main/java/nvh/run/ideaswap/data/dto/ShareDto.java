package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ShareDto {
    @IsObjectID
    private String id;
    @IsObjectID
    private String userID; // Chỉ lưu trữ ID của Users thay vì tham chiếu đầy đủ.
    @IsObjectID
    private String referenceID; // Lưu ID của đối tượng tham chiếu.
}
