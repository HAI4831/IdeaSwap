package nvh.run.ideaswap.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.validator.IsObjectID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
//c1
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User implements Serializable
{

    @Id
    @IsObjectID
    @JsonProperty("_id") // Giữ nguyên tên id khi trả về JSON
    private String id;
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100, message = "Tên không được vượt quá 100 ký tự")
    @Builder.Default
    private String firstName="";

    @NotBlank(message = "Họ không được để trống")
    @Size(max = 100, message = "Họ không được vượt quá 100 ký tự")
    @Builder.Default
    private String lastName="";

    @NotBlank(message = "Tên người dùng không được để trống")
    @Size(max = 50, message = "Tên người dùng không được vượt quá 50 ký tự")
    private String username;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Size(max = 320, message = "Email không được vượt quá 320 ký tự")
    private String email;

    @Size(max = 10, message = "Số điện thoại không được vượt quá 10 ký tự")
    private String phoneNumber;

    @Size(max = 1000, message = "Địa chỉ không được vượt quá 1000 ký tự")
    @Builder.Default
    private String address="";

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 7, message = "Mật khẩu phải có ít nhất 7 ký tự")
    @JsonIgnore // ẩn khi phản hồi json
    @Builder.Default
    private String password="$2a$10$ZD/EROx56XOvcutCg9jHxeXrz.iqMstXUCksTyvBb8gfD8SPPm7uW";

    @Builder.Default
    private String avatar="https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg";

    @NotNull(message = "gender can not null")
    @Builder.Default
    private Gender gender=Gender.Male;

    private int rating;

    @NotNull(message = "roleID không được trống")
    @IsObjectID
    private String roleID;
//    @DBRef
//    private Roles roleID;

    @Size(max = 5000, message = "Mô tả không được vượt quá 5000 ký tự")
    private String description;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Builder.Default
    private LocalDate birthday=LocalDate.of(1970, 1, 1);

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(User other) {
        if (other != null) {
            copyFromOther(other);
        }
    }
    @Field("__v")
    private Long version;

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
