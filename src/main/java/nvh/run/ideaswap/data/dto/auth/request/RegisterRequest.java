package nvh.run.ideaswap.data.dto.auth.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import nvh.run.ideaswap.common.validator.UniqueEmail;
import nvh.run.ideaswap.common.validator.UniqueUsername;
import nvh.run.ideaswap.data.entity.Gender;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 5, max = 100, message = "firstName phải có từ 5 đến 100 ký tự")
    private String firstName;

    @NotBlank(message = "Họ không được để trống")
    @Size(min = 5,max = 100, message = "lastName phải có từ 5 đến 100 ký tự")
    private String lastName;

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(min = 5, max = 50, message = "Tên người dùng phải có từ 5 đến 50 ký tự")
    @UniqueUsername
    private String username;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Size(max = 320, message = "Email phải có tối đa 320 ký tự")
    @UniqueEmail
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;

    private String phoneNumber;
    private String address;
    @Builder.Default
    private Gender gender=Gender.male;
    private int rating;
    private String description;
    private LocalDateTime birthday;
}
