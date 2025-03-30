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
@Document(collection = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Report implements Serializable
{
    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 1000, message = "Nội dung không được quá 150 kí tự ")
    private String content;

    @NotBlank(message = "ID tham chiếu không được để trống")
    @IsObjectID
    private String referenceID;

    @NotBlank(message = "ID người dùng không được để trống")
    @IsObjectID
    private String userID;
//    @DBRef
//    private Users userID;

    @NotBlank(message = "Loại không được để trống")
    @Size(max = 50, message = "Loại không được quá 50 kí tự ")
    private String type;

//    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status can not null")
    @Builder.Default
    private Status status=Status.pending;

    @NotBlank(message = "moderatorID không được để trống")
    private String moderatorID;
//    @DBRef
//    private Managers moderatorID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
