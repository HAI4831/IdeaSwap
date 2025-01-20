package nvh.run.ideaswap.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Gender;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerDTO {
    @IsObjectID
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
    private String avatar;
    private LocalDate birthday;
    private Gender gender;
    @IsObjectID
    private String roleId;
}

