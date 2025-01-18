package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "Reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Report {
    @Id
    private String id;

    @Field("content")
    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @Field("referenceID")
    @NotBlank(message = "ID tham chiếu không được để trống")
    private String referenceID;

    @Field("userID")
    @NotBlank(message = "ID người dùng không được để trống")
    private String userID;

    @Field("type")
    @NotBlank(message = "Loại không được để trống")
    private String type;

    @Field("status")
    @NotBlank(message = "Trạng thái không được để trống")
    private String status;

    @Field("moderatorID")
    private String moderatorID;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
