package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "Reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reports implements  java.io.Serializable , Cloneable {
    @Id
    private String id;

    @Field("content")
    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 1000, message = "Nội dung không được quá 150 kí tự ")
    private String content;

    @Field("referenceID")
    @NotBlank(message = "ID tham chiếu không được để trống")
//    @DBRef
    private ObjectId referenceID;

    @Field("userID")
    @NotBlank(message = "ID người dùng không được để trống")
    @DBRef
    private Users userID;

    @Field("type")
    @NotBlank(message = "Loại không được để trống")
    @Size(max = 50, message = "Loại không được quá 50 kí tự ")
    private String type;

    @Field("status")
    @NotBlank(message = "Trạng thái không được để trống")
    @Size(max = 50, message = "Trạng tháikhông được quá 50 kí tự ")
    private Status status;

    @Field("moderatorID")
    @NotBlank(message = "moderatorID không được để trống")
    @DBRef
    private Managers moderatorID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Override
    public Reports clone() {
        try {
            Reports clone = (Reports) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
