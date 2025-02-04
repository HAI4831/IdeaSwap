package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Gender;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
    private MultipartFile avatar;
    private Gender gender;
    private int rating;
    @IsObjectID
    private String roleID;
    private String description;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
