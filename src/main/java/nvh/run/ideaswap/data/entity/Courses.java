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

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "courses")
public class Course {

    @Id
    private String id;

    @NotBlank(message = "ID người dùng không được để trống")
    private String userID;

    @NotBlank(message = "ID danh mục không được để trống")
    private String categoryID;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được quá 255 ký tự")
    private String title;

    @Size(max = 1000, message = "Mô tả không được quá 1000 ký tự")
    private String description;

    private String imageUrl;

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
