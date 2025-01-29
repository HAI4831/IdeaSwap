package nvh.run.ideaswap.data.entity;

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
//c2
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "courses")
public class Courses
//        implements  java.io.Serializable , Cloneable
{

    @Id
    @IsObjectID
    private String id;

//    @NotBlank(message = "Courses tham chiếu tới ID người dùng không được để trống")
    @DBRef(lazy = true)
    private Users userID;

//    @NotBlank(message = "ID danh mục không được để trống")
    @DBRef
    private Categories categoryID;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 100, message = "Tiêu đề không được quá 100 ký tự")
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 5000, message = "Mô tả không được quá 5000 ký tự")
    private String description;

    @NotBlank(message = "imageUrl không được để trống")
    @Size(max = 150, message = "imageUrl không được quá 150 ký tự")
    private String imageUrl;

    @NotBlank(message = "view không được để trống")
    private int view;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Static method to simulate Cloudinary upload
    public static UploadResponse uploadFileToCloudinary(String file) {
        if (file == null || file.isEmpty()) {
            return new UploadResponse(false, "Thiếu thông tin", null);
        }
        // Simulate Cloudinary upload
        String uploadedUrl = "https://cloudinary.com/mock_image_url";
        return new UploadResponse(true, "Tải lên thành công", uploadedUrl);
    }

//    @Override
//    public Courses clone() {
//        try {
//            Courses clone = (Courses) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }

    public static class UploadResponse {
        private boolean status;
        private String message;
        private String imageUrl;

        public UploadResponse(boolean status, String message, String imageUrl) {
            this.status = status;
            this.message = message;
            this.imageUrl = imageUrl;
        }
    }
}
