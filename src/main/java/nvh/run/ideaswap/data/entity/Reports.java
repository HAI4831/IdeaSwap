package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
//c2
@Document(collection = "Reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reports
//        implements  java.io.Serializable , Cloneable
{
    @Id
    private ObjectId id;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 1000, message = "Nội dung không được quá 150 kí tự ")
    private String content;

    @NotBlank(message = "ID tham chiếu không được để trống")
    private ObjectId referenceID;

    @NotBlank(message = "ID người dùng không được để trống")
    private ObjectId userID;

    @NotBlank(message = "Loại không được để trống")
    @Size(max = 50, message = "Loại không được quá 50 kí tự ")
    private String type;

    @NotNull(message = "Status can not null")
    private Status status;

    @NotBlank(message = "moderatorID không được để trống")
    private ObjectId moderatorID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
