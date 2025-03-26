package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class ManagerRequest {
    @IsObjectID
    private String _id;
//    @IsObjectID
//    @JsonProperty("_id")
//    private String id;
    @IsObjectID
    private String roleID;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
    private String avatar;//base64
//    private MultipartFile avatar;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Gender gender;
}
