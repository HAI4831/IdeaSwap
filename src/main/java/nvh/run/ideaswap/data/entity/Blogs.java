package nvh.run.ideaswap.data.entity;
//c2

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "blogs")
public class Blogs
//        implements  java.io.Serializable , Cloneable
{

    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 5000, message = "Nội dung không được quá 5000 ký tự")
    private String content;

    @Size(max = 150, message = "URL không được quá 150 ký tự")
    private String url;

    @DBRef
    private Users userID;

    @DBRef
    private Categories categoryID;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

//    @Override
//    public Blogs clone() {
//        try {
//            Blogs clone = (Blogs) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
//    public String toJson() {
//        try {
//            return new ObjectMapper().writeValueAsString(this);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error converting to JSON", e);
//        }
//    }
}
