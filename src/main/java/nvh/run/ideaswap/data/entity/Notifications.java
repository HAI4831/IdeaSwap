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
import nvh.run.ideaswap.common.validator.IsObjectID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
//c1
@Document(collection = "Notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Notifications
//        implements  java.io.Serializable , Cloneable
{
    @Id
    @IsObjectID
    private String id;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 150, message = "imageUrl không được vượt quá 150 ký tự")
    private String imageUrl;

    @Builder.Default
    @NotBlank(message = "isUnRead không được để trống")
    private boolean isUnRead=true;

    @NotNull(message = "Danh sách người dùng không được để trống")
    @DBRef
    private List<Users> userIDs;

    @NotBlank(message = "ID diễn viên không được để trống")
    @IsObjectID
    private String actorID;

    @NotNull(message = "referenceType can not be null ")
    private ReferenceTypeEnum referenceType;

    @NotBlank(message = "isModal không được để trống")
    private boolean isModal;

    @NotBlank(message = "referenceID tham chiếu không được để trống")
    @IsObjectID
    private String referenceID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @Override
//    public Notifications clone() {
//        try {
//            Notifications clone = (Notifications) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
