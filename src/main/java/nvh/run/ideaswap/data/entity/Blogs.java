package nvh.run.ideaswap.data.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Document(collection = "blogs")
public class Blogs implements  java.io.Serializable , Cloneable {
    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }
    @Id
    private String id;
    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 5000, message = "Nội dung không được quá 5000 ký tự")
    private String content;

//    @NotBlank(message = "URL không được để trống")
    @Size(max = 1000, message = "URL không được quá 1000 ký tự")
    private String url;

    @Field(name="userID")
    @DBRef
//    @DocumentReference
    private Users userID;
//    private ObjectId userID;
//    private Object userID;
//    private String userID;

    @Field(name="categoryID")
//    @DocumentReference
    @DBRef
//    private String categoryID;
    private Categories categoryID;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Override
    public Blogs clone() {
        try {
            Blogs clone = (Blogs) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
