package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.NotBlank;
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
@Document(collection = "Shares")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Shares
//        implements  java.io.Serializable , Cloneable
{
    @Id
    @IsObjectID
    private String id;

    @NotBlank(message = "ID người dùng không được để trống")
    @DBRef
    private Users userID;

    @NotBlank(message = "ID tham chiếu không được để trống")
    @IsObjectID
//    @DBRef
    private String referenceID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @Override
//    public Shares clone() {
//        try {
//            Shares clone = (Shares) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
