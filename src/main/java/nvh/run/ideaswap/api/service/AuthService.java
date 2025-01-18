package nvh.run.authsystemgradle.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.authsystemgradle.common.exceptions.exception.custom.auth.DatabaseException;
import nvh.run.authsystemgradle.common.exceptions.exception.custom.auth.RoleNotFoundException;
import nvh.run.authsystemgradle.common.security.jwt.JwtUtilities;
import nvh.run.authsystemgradle.common.security.service.UserDetailsExtImpl;
import nvh.run.authsystemgradle.data.dto.auth.request.LoginRequest;
import nvh.run.authsystemgradle.data.dto.auth.request.LogoutRequest;
import nvh.run.authsystemgradle.data.dto.auth.request.RefreshTokenRequest;
import nvh.run.authsystemgradle.data.dto.auth.request.RegisterRequest;
import nvh.run.authsystemgradle.data.dto.share.ApiResponse;
import nvh.run.authsystemgradle.data.entity.Role;
import nvh.run.authsystemgradle.data.entity.User;
import nvh.run.authsystemgradle.data.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    IUserRepository iUserRepository;
    IRoleService iRoleService;
    PasswordEncoder passwordEncoder;
    JwtUtilities jwtUtilities;
    AuthenticationManager authenticationManager;

    public ResponseEntity<Object> register(RegisterRequest registerRequest) {
        try {
            Role role = iRoleService.findByName("user");
            User user = iUserRepository.save(User.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))  // Encode password
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .email(registerRequest.getEmail())
                    .roleID(role)
                    .phoneNumber("9999999999")
                    .rating(0)
                    .address("Ninh Bình")
                    .gender("Male")
                    .avatar("")
                    .description("new user nvh")
                    .birthday(LocalDateTime.now())
                    .build());
            return ResponseEntity.status(201).body(
                    Map.of(
                            "success", true,
                            "message", "User registered successfully",
                            "user", user
                    )
            );

        } catch (RoleNotFoundException e) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "message", e.getMessage()
                    )
            );
        } catch (DatabaseException e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "success", false,
                            "message", e.getMessage()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "success", false,
                            "message", "An unexpected error occurred",
                            "e",e,
                            "error", e.getMessage()
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse<User>> registerApi(RegisterRequest registerRequest) {
        try {
            // Lấy role mặc định
            Role role = iRoleService.findByName("user");

            // Tạo và lưu thông tin User
            User user = iUserRepository.save(User.builder()
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword())) // Encode password
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .email(registerRequest.getEmail())
                    .roleID(role)
                    .phoneNumber("9999999999")
                    .rating(0)
                    .address("Ninh Bình")
                    .gender("Male")
                    .avatar("")
                    .description("new user nvh")
                    .birthday(LocalDateTime.now())
                    .build());

            // Tạo và trả về response thành công
            ApiResponse<User> response = ApiResponse.<User>builder()
                    .status(201)
                    .success(true)
                    .message("User registered successfully")
                    .data(user)
                    .clazz(User.class) // Lưu kiểu dữ liệu User
                    .build();

            return ResponseEntity.status(201).body(response);

        } catch (RoleNotFoundException e) {
            // Trường hợp role không tìm thấy
            ApiResponse<User> response = ApiResponse.<User>builder()
                    .status(400)
                    .success(false)
                    .message(e.getMessage())
                    .clazz(User.class) // Đảm bảo consistency
                    .build();

            return ResponseEntity.badRequest().body(response);

        } catch (DatabaseException e) {
            // Trường hợp lỗi database
            ApiResponse<User> response = ApiResponse.<User>builder()
                    .status(500)
                    .success(false)
                    .message(e.getMessage())
                    .clazz(User.class)
                    .build();

            return ResponseEntity.status(500).body(response);

        } catch (Exception e) {
            // Trường hợp lỗi không xác định
            ApiResponse<User> response = ApiResponse.<User>builder()
                    .status(500)
                    .success(false)
                    .message("An unexpected error occurred")
                    .clazz(User.class)
                    .build();

            return ResponseEntity.status(500).body(response);
        }
    }

    @Override
    public ResponseEntity<Object> login(LoginRequest loginRequest) {
        try {
            // Tạo token xác thực từ username và password
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            // Thực hiện xác thực
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Tạo AccessToken và RefreshToken
            String accessToken = jwtUtilities.generateAccessToken(authentication);
            String refreshToken = jwtUtilities.generateRefreshToken(authentication);

            // Trả về response thành công
            return ResponseEntity.status(200).body(
                    Map.of(
                            "success", true,
                            "message", "Login successful!",
                            "accessToken", accessToken,
                            "refreshToken", refreshToken
                    )
            );
        } catch (Exception e) {
            // Trả về response khi có lỗi
            return ResponseEntity.status(401).body(
                    Map.of(
                            "success", false,
                            "message", "Login failed!",
                            "error", e.getMessage()
                    )
            );
        }
    }

    @Override
    public ResponseEntity<Object> getUserProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
//                Object principal = authentication.getPrincipal();
                UserDetailsExtImpl principal = (UserDetailsExtImpl)authentication.getPrincipal();

                log.info("Principal: {}", principal.toString());
                UserDetailsExtImpl user = (UserDetailsExtImpl) principal;
                String username = user.getUsername();
                String email = user.getEmail(); // Assuming email is a field in User
                String firstName = user.getFirstName(); // Populate based on User fields
                String lastName = user.getLastName();
                String phoneNumber = user.getPhoneNumber();
                String address = user.getAddress();
                String gender = user.getGender();
                LocalDateTime birthday = user.getBirthday();

                return ResponseEntity.ok(
                        Map.of(
                                "success", true,
                                "username", username,
                                "email", email,
                                "firstName", firstName,
                                "lastName", lastName,
                                "phoneNumber", phoneNumber,
                                "address", address,
                                "gender", gender,
                                "birthday", birthday
                        )
                );
            }

            return ResponseEntity.status(401).body(
                    Map.of(
                            "success", false,
                            "message", "User not authenticated"
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "success", false,
                            "message", "Error retrieving user profile",
                            "error", e.getMessage()
                    )
            );
        }
    }

    @Override
    public ResponseEntity<Object> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String token = refreshTokenRequest.getRefreshToken();
            // Verify the provided refresh token
            if (!jwtUtilities.verifySignedToken(token)) {
                return ResponseEntity.status(401).body(
                        Map.of(
                                "success", false,
                                "message", "Invalid or expired refresh token"
                        )
                );
            }

            // Extract username and claims from the verified refresh token
            String username = jwtUtilities.extractUsername(token);

            // Retrieve the user from the database
            User user = iUserRepository.findByUsername(username)
                    .orElseThrow(() -> new DatabaseException("User not found"));

            // Create authentication object
            UserDetailsExtImpl userDetails = UserDetailsExtImpl.build(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Generate a new access token
            String newAccessToken = jwtUtilities.generateAccessToken(authentication);

            // Respond with the new access token
            return ResponseEntity.status(200).body(
                    Map.of(
                            "success", true,
                            "message", "Token refreshed successfully",
                            "accessToken", newAccessToken
                    )
            );
        } catch (DatabaseException e) {
            return ResponseEntity.status(404).body(
                    Map.of(
                            "success", false,
                            "message", e.getMessage()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "success", false,
                            "message", "An unexpected error occurred",
                            "error", e.getMessage()
                    )
            );
        }
    }

    @Override
    public ResponseEntity<Object> logout(LogoutRequest request) {
        return null;
    }

}
