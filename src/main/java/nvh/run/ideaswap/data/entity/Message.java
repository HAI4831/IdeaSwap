package nvh.run.ideaswap.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.validator.IsObjectID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
//c2
@Document(collection = "Messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Message implements Serializable
{
    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotNull(message = "Người gửi không được để trống")
    @IsObjectID
    private String senderID;
//    @DBRef
//    private Users senderID;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 500, message = "Nội dung không được vượt quá 500 ký tự")
    private String content;

    @IsObjectID
    private String messageParentID;

    @NotNull(message = "Cuộc trò chuyện không được để trống")
    @IsObjectID
    private String conversationID;
//    @DBRef
//    private Conversations conversationID;

    private String fileUrl;

    @NotBlank(message = "Loại không được để trống")
    private String type;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
