package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ManagerRequest {
    private String id;
    @IsObjectID
    private String roleID;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
    private String avatar;
    private LocalDate birthday= LocalDate.of(1970,1,1);
    private Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
