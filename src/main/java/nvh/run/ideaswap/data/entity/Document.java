package nvh.run.ideaswap.data.entity;

//import jakarta.persistence.Column;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.validator.IsObjectID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
//c1
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@org.springframework.data.mongodb.core.mapping.Document(collection = "documents")
public class Document implements Serializable
{

    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotBlank(message = "Documents reference ID người dùng không được để trống")
    private String userID;
    @IsObjectID
    private String categoryID;
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 100, message = "Tiêu đề không được quá 100 ký tự")
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 5000, message = "Mô tả không được quá 5000 ký tự")
    private String description;

    @NotBlank(message = "fileUrl không được để trống")
    @Size(max = 100, message = "URL tệp không được quá 100 ký tự")
    private String fileUrl;
    @Builder.Default
    private int countDownload=0;

    @NotBlank(message = "imageUrl không được để trống")
    @Size(max = 150, message = "URL tệp không được quá 150 ký tự")
    private String imageUrl;

    @Builder.Default
    @NotNull(message = "Status cannot be null")
    private Status status=Status.pending;

    @Builder.Default
    @JsonSetter(nulls = Nulls.SKIP)
    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score must be at least 0")
    private Double score=0d; //Double là đối tượng double cho phép lưu null

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