package nvh.run.ideaswap.data.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nvh.run.ideaswap.common.validator.IsObjectID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
//c1
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Users
//        implements  java.io.Serializable , Cloneable
{

    @Id
    @IsObjectID
//    @Field("_id") // Xác định rõ id trong MongoDB
    @JsonProperty("_id") // Giữ nguyên tên id khi trả về JSON
//    @JsonAlias("id")
    private String id;
//    // Custom getter để trả về trường "_id"
//    @JsonGetter("_id")
//    public String get_Id() {
//        return id;
//    }
//
//    // Custom setter để nhận giá trị cho trường "id"
//    @JsonSetter("id")
//    public void setId(String id) {
//        this.id = id;
//    }

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
//    @UniqueUsername
    private String username;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    @Size(max = 320, message = "Email không được vượt quá 320 ký tự")
//    @UniqueUsername
    private String email;

    @Size(max = 10, message = "Số điện thoại không được vượt quá 10 ký tự")
//    @UniquePhoneNumber
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
    private Gender gender=Gender.male;

    private int rating;

    @DBRef
//    private Object roleID;
    private Roles roleID;

    @Size(max = 5000, message = "Mô tả không được vượt quá 5000 ký tự")
    private String description;

    @Builder.Default
    private LocalDate birthday=LocalDate.of(1970, 1, 1);

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Users(Users other) {
        if (other != null) {
            copyFromOther(other);
        }
    }
    @Field("__v")
    private Long version;

    private void copyFromOther(Users other) {
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

//    @Override
//    public Users clone() {
//        try {
//            Users clone = (Users) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
