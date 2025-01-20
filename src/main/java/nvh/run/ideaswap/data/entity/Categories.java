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
@Document(collection = "categories")
public class Categories implements  java.io.Serializable , Cloneable {
    @Id
    private String id;
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 200, message = "Tên danh mục không được quá 200 ký tự")
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(max = 5000, message = "Mô tả không được quá 500 ký tự")
    private String description;
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Override
    public Categories clone() {
        try {
            Categories clone = (Categories) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
