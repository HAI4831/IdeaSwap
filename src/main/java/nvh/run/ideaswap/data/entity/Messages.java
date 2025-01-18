package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "Messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Messages {
    @Id
    private String id;

    @Field("senderID")
    @DBRef
    @NotNull(message = "Người gửi không được để trống")
    private Users senderId;

    @Field("content")
    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 500, message = "Nội dung không được vượt quá 500 ký tự")
    private String content;

    @Field("messageParentID")
    @DBRef
//    @Column(nullable = true)
    private String messageParentID;

    @Field("conversationID")
    @DBRef
    @NotNull(message = "Cuộc trò chuyện không được để trống")
    private Conversations conversation;

    @Field("fileUrl")
    private String fileUrl;

    @Field("type")
    @NotBlank(message = "Loại không được để trống")
    private String type;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
