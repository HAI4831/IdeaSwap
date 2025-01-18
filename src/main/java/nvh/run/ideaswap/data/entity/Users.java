package nvh.run.ideaswap.data.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.UniqueUsername;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @Id
    private String id;

    @Field("firstName")
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    private String firstName;

    @Field("lastName")
    @NotBlank(message = "Họ không được để trống")
    @Size(max = 100, message = "Họ không được vượt quá 100 ký tự")
    private String lastName;

    @Field("username")
    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(max = 50, message = "Tên người dùng không được vượt quá 50 ký tự")
    @UniqueUsername
    private String username;

    @Field("email")
    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Size(max = 320, message = "Email không được vượt quá 320 ký tự")
    @UniqueUsername
    private String email;

    @Field("phoneNumber")
    @Size(max = 10, message = "Số điện thoại không được vượt quá 10 ký tự")
    private String phoneNumber;

    @Field("address")
    @Size(max = 1000, message = "Địa chỉ không được vượt quá 1000 ký tự")
    private String address;

    @Field("password")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 7, message = "Mật khẩu phải có ít nhất 7 ký tự")
    private String password;

    @Field("avatar")
    private String avatar;

    @Field("gender")
    private String gender;

    @Field("rating")
    private int rating;

    @DBRef
    @Field("roleID")
    private Roles roleID;

    @Field("description")
    @Size(max = 5000, message = "Mô tả không được vượt quá 5000 ký tự")
    private String description;

    @Field("birthday")
    private LocalDateTime birthday;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(User other) {
        if (other != null) {
            copyFromOther(other);
        }
    }

    private void copyFromOther(User other) {
        this.id = other.id;
        this.firstName = other.firstName;
        this.lastName = other.lastName;
        this.username = other.username;
        this.email = other.email;
        this.phoneNumber = other.phoneNumber;
        this.address = other.address;
        this.password = other.password;
        this.avatar = other.avatar;
        this.birthday = other.birthday;
        this.gender = other.gender;
        this.roleID = other.roleID;
        this.description = other.description;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
    }
}
