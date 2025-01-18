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

@Document(collection = "Videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Videos {
    @Id
    private String id;

    @Field("userID")
    @NotBlank(message = "ID người dùng không được để trống")
    @DBRef
    private Users userID;

    @Field("title")
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 100, message = "Tiêu đề không được quá 100 kí tự")
    private String title;

    @Field("description")
    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 5000, message = "Mô tả không được vượt quá 5000 ký tự")
    private String description;

    @Field("imageUrl")
    @NotBlank(message = "imageUrl không được để trống")
    @Size(max = 150, message = "imageUrl không được vượt quá 150 ký tự")
    private String imageUrl;

    @Field("videoUrl")
    @NotBlank(message = "videoUrl không được để trống")
    @Size(max = 100, message = "videoUrl không được vượt quá 100 ký tự")
    private String videoUrl;

    @Field("view")
    @NotBlank(message = "view không được để trống")
    private int view;

    @Field("courseID")
    @NotBlank(message = "courseID không được để trống")
    @DBRef
    private Courses courseID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
