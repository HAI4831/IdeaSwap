package nvh.run.ideaswap.data.entity;

//import jakarta.persistence.Column;

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
import java.util.Date;

//c1
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "codes")
public class Codes implements Serializable
{

    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotBlank(message = "Code cannot be empty")
    @Builder.Default
    private int code=0;

    @NotBlank(message = "Code expiration cannot be empty")
    private Date codeExpiration;

    @Size(max = 255)
    private String userEmail;// tham chiếu tới email của user có kiểu string

    @IsObjectID
    private String userID;
//    @DBRef
//    private Users user;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}
