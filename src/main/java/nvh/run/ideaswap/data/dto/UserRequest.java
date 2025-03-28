package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.validator.IsObjectID;
import nvh.run.ideaswap.data.entity.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserRequest {
    @IsObjectID
    @JsonProperty("_id")
    private String id;
    @Builder.Default
    private String firstName="";
    @Builder.Default
    private String lastName="";
    private String username;
    private String email;
    private String phoneNumber;
    @Builder.Default
    private String address="";
    @Builder.Default
    private String password="$2a$10$ZD/EROx56XOvcutCg9jHxeXrz.iqMstXUCksTyvBb8gfD8SPPm7uW";
    private String imageBase64;//base64
    private String avatar;
//    private MultipartFile avatar;
    @Builder.Default
    private Gender gender=Gender.Male;
    @Builder.Default
    private Integer rating = 0 ;
    @IsObjectID
    private String roleID;
    private String description;
    @Builder.Default
    private LocalDate birthday=LocalDate.of(1970, 1, 1);
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
