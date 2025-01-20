package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Gender;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @IsObjectID
    private String id;
    @JsonProperty("firstName")
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String avatar;
    private Gender gender;
    private int rating;
    private String description;
    private LocalDateTime birthday;
}

