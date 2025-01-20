package nvh.run.ideaswap.data.entity;

//import jakarta.persistence.Column;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
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
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "Notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Notifications implements  java.io.Serializable , Cloneable {
    @Id
    private String id;

    @Field("description")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;

    @Field("imageUrl")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 150, message = "imageUrl không được vượt quá 150 ký tự")
    private String imageUrl;

    @Field("isUnRead")
    @NotBlank(message = "isUnRead không được để trống")
//    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT 'TRUE'")
    private boolean isUnRead=true;

    @Field("userIDs")
    @NotNull(message = "Danh sách người dùng không được để trống")
    @DBRef
    private List<Users> userIDs;

    @Field("actorID")
    @NotBlank(message = "ID diễn viên không được để trống")
//    @DBRef
    private ObjectId actorID;

    @Field("referenceType")
    @NotBlank(message = "referenceType không được để trống")
    @Size(max = 50, message = "referenceType không được vượt quá 50 ký tự")
//    @Enumerated(EnumType.STRING)
    private ReferenceTypeEnum referenceType;

    @Field("isModal")
    @NotBlank(message = "isModal không được để trống")
    private boolean isModal;

    @Field("referenceID")
    @NotBlank(message = "referenceID tham chiếu không được để trống")
    private ObjectId referenceID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Override
    public Notifications clone() {
        try {
            Notifications clone = (Notifications) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
