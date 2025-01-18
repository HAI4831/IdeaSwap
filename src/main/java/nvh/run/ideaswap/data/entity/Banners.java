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
@Document(collection = "banners")
public class Banners {
    @Id
    private String id;

    @Field("managerID")
    @DBRef
    private Managers manager;

    @Field("name")
    @NotBlank(message = "Tên banner không được để trống")
    @Size(max = 30, message = "Tên banner không được quá 30 ký tự")
    private String name;

    @Field("site")
    @NotBlank(message = "URL không được để trống")
    @Size(max = 30, message = "Site không được quá 30 ký tự")
    private String site;

    @Field("imageUrl")
    @NotBlank(message = "URL hình ảnh không được để trống")
    @Size(max = 150, message = "URL hình ảnh không được quá 150 ký tự")
    private String imageUrl;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
