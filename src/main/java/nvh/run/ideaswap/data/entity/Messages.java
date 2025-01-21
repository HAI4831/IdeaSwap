package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
//c2
@Document(collection = "Messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Messages
//        implements  java.io.Serializable , Cloneable
{
    @Id
    @IsObjectID
    private String id;

    @DBRef
    @NotNull(message = "Người gửi không được để trống")
    private Users senderID;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 500, message = "Nội dung không được vượt quá 500 ký tự")
    private String content;

    @IsObjectID
    private String messageParentID;

    @DBRef
    @NotNull(message = "Cuộc trò chuyện không được để trống")
    private Conversations conversationID;

    private String fileUrl;

    @NotBlank(message = "Loại không được để trống")
    private String type;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @Override
//    public Messages clone() {
//        try {
//            Messages clone = (Messages) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
