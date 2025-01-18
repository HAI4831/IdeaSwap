package nvh.run.ideaswap.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class VideosDto {
    private String id;

    private String userID; // Chỉ lưu trữ ID của Users thay vì tham chiếu đầy đủ.

    private String title;

    private String description;

    private String imageUrl;

    private String videoUrl;

    private int view;

    private String courseID; // Chỉ lưu trữ ID của Courses thay vì tham chiếu đầy đủ.

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
