package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Status;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentRequest {
    @IsObjectID
    @JsonProperty("_id")
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
    @Builder.Default
    @JsonSetter(nulls = Nulls.SKIP)
    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score must be at least 0")
    private Double score=0d; //Double là đối tượng double cho phép lưu null
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
