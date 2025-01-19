package nvh.run.ideaswap.data.entity;

//import jakarta.persistence.Column;

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
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "managers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Managers {
    @Id
    private String id;

    @Field("firstName")
    @NotBlank(message = "Họ không được để trống")
    @Size(max = 100,message = "firstName không quá 100 kí tự")
    private String firstName;

    @Field("lastName")
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100 , message = "lastName không quá 100 kí tự")
    private String lastName;

    @Field("username")
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(max = 50 , message = "username không quá 50 kí tự")
//    @UniqueUsernameManager
    private String username;

    @Field("email")
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email phải hợp lệ")
    @Size(max = 320, message = "Email không quá 320 kí tự")
//    @UniqueEmail
    private String email;

    @Field("phoneNumber")
    @NotBlank(message = "Số điện thoại không được để trống")
    @Size(max = 10, message = "phoneNumber không quá 10 kí tự")
    private String phoneNumber;

    @Field("address")
    @Size(max = 1000, message = "address không quá 1000 kí tự")
    private String address;

    @Field("password")
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 8, message = "Mật khẩu phải ít nhất 8 ký tự")
//    @Column(nullable = false, columnDefinition = "VARCHAR(150) DEFAULT '$2a$10$ZD/EROx56XOvcutCg9jHxeXrz.iqMstXUCksTyvBb8gfD8SPPm7uW'")
    private String password;

    @Field("avatar")
//    @Column(nullable = false, columnDefinition = "VARCHAR(150) DEFAULT 'https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg'")
    @Size(max = 150, message = "avatar không quá 150 ký tự")
    private String avatar;

    @Field("birthday")
//    @Column(nullable = false, columnDefinition = "VARCHAR(150) DEFAULT '01/01/1970'")
    private LocalDate birthday= LocalDate.of(1970,1,1);

    @Field("gender")
//    @Column(nullable = false, columnDefinition = "VARCHAR(150) DEFAULT 'Male'")
    private String gender="Male";

    @Field("roleID")
    @DBRef
    private Object roleID;
//    private Roles roleID;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
