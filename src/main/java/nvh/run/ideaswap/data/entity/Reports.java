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
@Document(collection = "Reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reports
//        implements  java.io.Serializable , Cloneable
{
    @Id
    @IsObjectID
    private String id;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 1000, message = "Nội dung không được quá 150 kí tự ")
    private String content;

    @NotBlank(message = "ID tham chiếu không được để trống")
    @IsObjectID
//    @DBRef
    private String referenceID;

//    @NotBlank(message = "ID người dùng không được để trống")
    @DBRef
    private Users userID;

    @NotBlank(message = "Loại không được để trống")
    @Size(max = 50, message = "Loại không được quá 50 kí tự ")
    private String type;

    @NotNull(message = "Status can not null")
    @Builder.Default
    private Status status=Status.pending;

//    @NotBlank(message = "moderatorID không được để trống")
    @DBRef
    private Managers moderatorID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @Override
//    public Reports clone() {
//        try {
//            Reports clone = (Reports) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
