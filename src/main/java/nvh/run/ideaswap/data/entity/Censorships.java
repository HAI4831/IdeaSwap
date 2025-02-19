package nvh.run.ideaswap.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
//c0
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "censorships")
public class Censorships implements Serializable
{

    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @IsObjectID
    private String contentID;//blogID

    @NotNull(message = "Status cannot be null")
    @Builder.Default
    private Status status=Status.pending;

    @NotBlank(message = "Phản hồi không được để trống")
    @Size(max = 500, message = "Phản hồi không được quá 500 ký tự")
    private String feedback;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}