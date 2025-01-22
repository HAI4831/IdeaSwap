package nvh.run.ideaswap.data.entity;

//import jakarta.persistence.Column;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
//c1
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "codes")
public class Codes
{

    @Id
    private ObjectId id;

    @NotBlank(message = "Code cannot be empty")
    private int code=0;

    @NotBlank(message = "Code expiration cannot be empty")
    private LocalDateTime codeExpiration;

    @Size(max = 255)
    private ObjectId userEmail;

    private ObjectId user;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

}
