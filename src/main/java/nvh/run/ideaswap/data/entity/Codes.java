package nvh.run.ideaswap.data.entity;

//import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Document(collection = "codes")
public class Codes implements  java.io.Serializable , Cloneable {
    @Id
    private String id;

    @NotBlank(message = "Code cannot be empty")

//    @Column(name = "code",nullable = false,unique = true,length = 10,columnDefinition = "INT UNSIGNED DEFAULT 0")
    @Field("code")
    private int code;

    @NotBlank(message = "Code expiration cannot be empty")
    private LocalDateTime codeExpiration;

    @NotBlank(message = "User email cannot be empty")
    @Size(max = 255)
    @Field("userEmail")
    private String userEmail;

    @DBRef(lazy = true)
    private Users user;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Override
    public Codes clone() {
        try {
            Codes clone = (Codes) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
