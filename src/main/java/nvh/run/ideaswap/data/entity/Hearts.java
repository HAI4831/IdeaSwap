package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
//c1
@Document(collection = "hearts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Hearts
//        implements  java.io.Serializable , Cloneable
{
    @Id
    @IsObjectID
    private String id;

    @DBRef
    @NotNull(message = "Người dùng không được để trống")
    private Users userID;

    @NotBlank(message = "ID tham chiếu không được để trống")
    @IsObjectID
    private String referenceID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @Override
//    public Hearts clone() {
//        try {
//            Hearts clone = (Hearts) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
