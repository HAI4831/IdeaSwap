package nvh.run.authsystemgradle.data.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterRequest {

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 5, max = 100, message = "firstName phải có từ 5 đến 100 ký tự")
    private String firstName;

    @NotBlank(message = "Họ không được để trống")
    @Size(min = 5,max = 100, message = "lastName phải có từ 5 đến 100 ký tự")
    private String lastName;

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(min = 5, max = 50, message = "Tên người dùng phải có từ 5 đến 50 ký tự")
    private String username;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Size(max = 320, message = "Email phải có tối đa 320 ký tự")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;
}
