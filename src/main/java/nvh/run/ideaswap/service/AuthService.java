package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.exceptions.exception.custom.auth.DatabaseException;
import nvh.run.ideaswap.common.security.jwt.JwtUtilities;
import nvh.run.ideaswap.common.security.service.UserDetailsExtImpl;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.dto.auth.request.LoginRequest;
import nvh.run.ideaswap.data.dto.auth.request.LogoutRequest;
import nvh.run.ideaswap.data.dto.auth.request.RefreshTokenRequest;
import nvh.run.ideaswap.data.dto.auth.request.RegisterRequest;
import nvh.run.ideaswap.data.dto.auth.response.*;
import nvh.run.ideaswap.data.entity.Gender;
import nvh.run.ideaswap.data.entity.Roles;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.IUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    IUserRepository iUserRepository;
    RoleService roleService;
    PasswordEncoder passwordEncoder;
    JwtUtilities jwtUtilities;
    AuthenticationManager authenticationManager;
    NotificationService notificationService;
    private final UserService userService;

    public RegisterResponse register(RegisterRequest registerRequest) {
            Roles role = roleService.findByName("user");
            Users user;
            try {
                if (iUserRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
                    throw new RuntimeException("Can't register new user because the user already exists.");
                }
            } catch (Exception e) {
                throw new RuntimeException("An error occurred while checking the user: " + e.getMessage(), e);
            }
            try {
                 user= iUserRepository.save(
                         //map
                         Users.builder()
                        .username(registerRequest.getUsername())
                        .password(registerRequest.getPassword() ==null ? passwordEncoder.encode("abCD@1234") : passwordEncoder.encode(registerRequest.getPassword()))  // Encode password
                        .firstName(registerRequest.getFirstName()==null ? "":registerRequest.getFirstName())
                        .lastName(registerRequest.getLastName()==null?"":registerRequest.getLastName())
                        .email(registerRequest.getEmail())
                        .roleID(role.getId())
                        .phoneNumber(registerRequest.getPhoneNumber() == null ? "" : registerRequest.getPhoneNumber() )
                        .rating(0)
                        .address(registerRequest.getAddress() == null ? "Ninh Bình" : registerRequest.getAddress())
                        .gender(registerRequest.getGender() == null ? Gender.male : registerRequest.getGender())
                        .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
                        .description(registerRequest.getDescription()==null ? "" :registerRequest.getDescription() )
                        .birthday(registerRequest.getBirthday() == null ? LocalDate.parse("01/01/1970") : LocalDate.from(registerRequest.getBirthday()))
                        .build());
            }
            catch (Exception e){
                throw new DatabaseException("Register failed for user ", e);
            }
            notificationService.createNotification(
                    NotificationRequest.builder()
                            .id(null)
                            .userIDs(List.of(user.getId()))
                            .description("A new user just registered")
                            .imageUrl(user.getAvatar())
                    .build()
            );
            return RegisterResponse.builder()
                    .user(user)
                    .build();
    }

    public LoginResponse login(LoginRequest loginRequest) {
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
            return LoginResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Login failed for user " + loginRequest.getUsername(), e);
        }
    }

    @Cacheable(value = "user_profile_response")
    public UserProfileResponse getUserProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Users user = userService.findByUsername(authentication.getName());
            if(user!=null) return UserProfileResponse.builder().user(user).authenticated(true).build();
//            if (authentication != null && authentication.isAuthenticated()) {
//                log.info("Principal: {}", authentication.getPrincipal().toString());
//                UserDetailsExtImpl principal = (UserDetailsExtImpl)authentication.getPrincipal();
////                log.info("Principal: {}", principal.toString());
//                UserDetailsExtImpl user = (UserDetailsExtImpl) principal;
//                return ProfileResponse.builder()
//                        .user(user)
//                        .authenticated(true)
//                        .build();
//            }
            return UserProfileResponse.builder()
                    .user(null)
                    .authenticated(false)
                    .build();
        } catch (Exception e) {
//            throw new RuntimeException("Unexpected principal type: " + principal.getClass().getName());
            throw new RuntimeException("Error retrieving user profile",e);
        }
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String token = refreshTokenRequest.getRefreshToken();
            // Verify the provided refresh token
            if (!jwtUtilities.verifySignedToken(token)) {
                return RefreshTokenResponse.builder()
                        .accessToken(null)
                        .tokenIsValid(false)
                        .build();
            }

            // Extract username and claims from the verified refresh token
            String username = jwtUtilities.extractUsername(token);

            // Retrieve the user from the database
            Users user = iUserRepository.findByUsername(username)
                    .orElseThrow(() -> new DatabaseException("User not found"));

            Roles role = roleService.getRoleById(user.getRoleID());
            // Create authentication object
            UserDetailsExtImpl userDetails = new UserDetailsExtImpl(user, Collections.singleton(new SimpleGrantedAuthority(role.getName())));
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            // Generate a new access token
            String newAccessToken = jwtUtilities.generateAccessToken(authentication);

            // Respond with the new access token
            return RefreshTokenResponse.builder()
                    .accessToken(newAccessToken)
                    .tokenIsValid(true)
                    .build();
        } catch (DatabaseException e) {
            throw new RuntimeException("Error refreshing token", e);
        }
    }

    public ResponseEntity<Object> logout(LogoutRequest request) {
        return null;
    }
}
