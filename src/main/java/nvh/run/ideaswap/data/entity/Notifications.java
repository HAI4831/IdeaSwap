package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Notification {
    @Id
    private String id;

    @Field("description")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 255, message = "Mô tả không được vượt quá 255 ký tự")
    private String description;

    @Field("imageUrl")
    private String imageUrl;

    @Field("isUnRead")
    private boolean isUnRead;

    @Field("userIDs")
    @NotNull(message = "Danh sách người dùng không được để trống")
    private List<String> userIDs;

    @Field("actorID")
    @NotBlank(message = "ID diễn viên không được để trống")
    private String actorID;

    @Field("referenceType")
    private String referenceType;

    @Field("isModal")
    private boolean isModal;

    @Field("referenceID")
    @NotBlank(message = "ID tham chiếu không được để trống")
    private String referenceID;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
