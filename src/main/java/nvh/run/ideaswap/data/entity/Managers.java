package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "managers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Manager {
    @Id
    private String id;

    @Field("firstName")
    @NotBlank(message = "Họ không được để trống")
    private String firstName;

    @Field("lastName")
    @NotBlank(message = "Tên không được để trống")
    private String lastName;

    @Field("username")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @Field("email")
    @Email(message = "Email phải hợp lệ")
    private String email;

    @Field("phoneNumber")
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phoneNumber;

    @Field("address")
    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @Field("password")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải ít nhất 8 ký tự")
    private String password;

    @Field("avatar")
    private String avatar;

    @Field("birthday")
    private LocalDate birthday;

    @Field("gender")
    private String gender;

    @Field("roleID")
    private String roleID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
