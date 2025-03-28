package nvh.run.ideaswap.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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
//c1
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "comments")
public class Comment implements Serializable
{

    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 1000, message = "Nội dung không được quá 1000 ký tự")
    private String content;

    @IsObjectID
    private String parentCommentID;

    @NotBlank(message = "ID người dùng không được để trống")
    @IsObjectID
    private String userID;
//    @DBRef
//    private Users userID;

    @IsObjectID
    private String referenceID;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}
