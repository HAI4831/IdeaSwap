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
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "Videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Video {
    @Id
    private String id;

    @Field("userID")
    @NotBlank(message = "ID người dùng không được để trống")
    private String userID;

    @Field("title")
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @Field("description")
    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    private String description;

    @Field("imageUrl")
    private String imageUrl;

    @Field("videoUrl")
    private String videoUrl;

    @Field("view")
    private int view;

    @Field("courseID")
    private String courseID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
