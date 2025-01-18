package nvh.run.ideaswap.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ShareDto {
    private String id;

    private String userID; // Chỉ lưu trữ ID của Users thay vì tham chiếu đầy đủ.

    private String referenceID; // Lưu ID của đối tượng tham chiếu.

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
