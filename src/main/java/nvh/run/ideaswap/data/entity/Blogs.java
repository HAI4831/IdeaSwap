package nvh.run.ideaswap.data.entity;

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
public class Blog {
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
    private User user;

    @Field(name="categoryID")
    @DBRef
    private Category category;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
