package nvh.run.ideaswap.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.validator.IsObjectID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
//c1
@Document(collection = "hearts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Heart implements Serializable
{
    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotNull(message = "Người dùng không được để trống")
    @IsObjectID
    private String userID;
//    @DBRef
//    private Users userID;

    @NotBlank(message = "ID tham chiếu không được để trống")
    @IsObjectID
    private String referenceID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
