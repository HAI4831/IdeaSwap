package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Status;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
public class DocumentRequest {
    private String id;
    @IsObjectID
    private String userID;
    @IsObjectID
    private String categoryID;
    private String title;
    private String description;
    private String fileUrl;
    @Builder.Default
    private int countDownload=0;
    private MultipartFile imageUrl;
    @Builder.Default
    private Status status=Status.pending;
    private double score;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
