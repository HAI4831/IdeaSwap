package nvh.run.ideaswap.data.entity;

//import jakarta.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
//c1
@Document(collection = "managers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Managers
//        implements  java.io.Serializable , Cloneable
{
    public Managers(Managers other) {
        if (other != null) {
            copyFromOther(other);
        }
    }
    private void copyFromOther(Managers other) {
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
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;
    }
    @Id
    @IsObjectID
    @JsonProperty("_id")
    private String id;

    @NotBlank(message = "Họ không được để trống")
    @Size(max = 100,message = "firstName không quá 100 kí tự")
    private String firstName;

    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100 , message = "lastName không quá 100 kí tự")
    private String lastName;

    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(max = 50 , message = "username không quá 50 kí tự")
//    @UniqueUsernameManager
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email phải hợp lệ")
    @Size(max = 320, message = "Email không quá 320 kí tự")
//    @UniqueEmail
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 10, message = "phoneNumber không quá 10 kí tự")
//    @UniquePhoneNumber
    private String phoneNumber;

    @Size(max = 1000, message = "address không quá 1000 kí tự")
    private String address;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải ít nhất 8 ký tự")
    private String password;

    @Size(max = 150, message = "avatar không quá 150 ký tự")
    private String avatar;

    private LocalDate birthday;

    @NotNull(message = "gender can not null")
    @Builder.Default
   private Gender gender=Gender.male;

    @DBRef
    private Roles roleID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

//    @Override
//    public Managers clone() {
//        try {
//            Managers clone = (Managers) super.clone();
//            // TODO: copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError();
//        }
//    }
}
//    @Column(nullable = false, columnDefinition = "VARCHAR(150) DEFAULT 'Male'")
//    @Column(nullable = false, columnDefinition = "VARCHAR(150) DEFAULT '01/01/1970'")
//    @Column(nullable = false, columnDefinition = "VARCHAR(150) DEFAULT 'https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg'")
//    @Column(nullable = false, columnDefinition = "VARCHAR(150) DEFAULT '$2a$10$ZD/EROx56XOvcutCg9jHxeXrz.iqMstXUCksTyvBb8gfD8SPPm7uW'")

