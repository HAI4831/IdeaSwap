package nvh.run.ideaswap.data.entity;

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
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
//c1
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "banners")
public class Banners implements Serializable
{

    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotBlank(message = "manager của banner không thể trống")
    @IsObjectID
    private String managerID;
//    @DBRef
//    private Managers managerID;

    @NotBlank(message = "Tên banner không được để trống")
    @Size(max = 30, message = "Tên banner không được quá 30 ký tự")
    private String name;

    @NotBlank(message = "URL không được để trống")
    @Size(max = 30, message = "Site không được quá 30 ký tự")
    private String site;

    @NotBlank(message = "URL hình ảnh không được để trống")
    @Size(max = 150, message = "URL hình ảnh không được quá 150 ký tự")
    private String imageUrl;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}
