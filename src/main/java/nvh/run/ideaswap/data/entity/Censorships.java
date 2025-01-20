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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "censorships")
public class Censorships implements  java.io.Serializable , Cloneable {
    @Id
    private String id;

    @Field(name="contentID")
    @DBRef
    private ObjectId contentID;

//    @NotBlank(message = "Trạng thái không được để trống")
//    @Size(max = 10, message = "Trạng thái không được quá 10 ký tự")

//    @Enumerated(EnumType.STRING)
//    @Column(name = "status", nullable = false, columnDefinition = "varchar(10) default 'pending'")// dùng cho jpa
    @Field(name = "status")
    private Status status=Status.PENDING;

    @NotBlank(message = "Phản hồi không được để trống")
    @Size(max = 500, message = "Phản hồi không được quá 500 ký tự")
    private String feedback;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Override
    public Censorships clone() {
        try {
            Censorships clone = (Censorships) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
