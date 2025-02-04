package nvh.run.ideaswap.data.entity;

//import jakarta.persistence.Column;

import jakarta.validation.constraints.NotBlank;
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
import java.util.Date;

//c1
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "codes")
public class Codes
//        implements  java.io.Serializable , Cloneable
{

    @Id
    @IsObjectID
    private String id;

    @NotBlank(message = "Code cannot be empty")
    @Builder.Default
    private int code=0;

    @NotBlank(message = "Code expiration cannot be empty")
//    private LocalDateTime codeExpiration;
    private Date codeExpiration;

    @Size(max = 255)
    private String userEmail;// tham chiếu tới email của user có kiểu string

    @DBRef
    private Users user;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

//    @Override
//    public Codes clone() {
//        try {
//            Codes clone = (Codes) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
//    @Column(name = "code",nullable = false,unique = true,length = 10,columnDefinition = "INT UNSIGNED DEFAULT 0")
