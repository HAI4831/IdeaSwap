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
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
//c0
@Document(collection = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Roles
//        implements  java.io.Serializable , Cloneable
{
    @Id
    @IsObjectID
    private String id;

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @Override
//    public Roles clone() {
//        try {
//            Roles clone = (Roles) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
