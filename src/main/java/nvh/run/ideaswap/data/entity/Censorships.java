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
@Document(collection = "censorships")
public class Censorships {
    @Id
    private String id;

    @Field(name="contentID")
    @DBRef
    private String contentID;

    @NotBlank(message = "Trạng thái không được để trống")
    @Size(max = 20, message = "Trạng thái không được quá 20 ký tự")
    private String status; // approved, pending, rejected

    @Size(max = 500, message = "Phản hồi không được quá 500 ký tự")
    private String feedback;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
