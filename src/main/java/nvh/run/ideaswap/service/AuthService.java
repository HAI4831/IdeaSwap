package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.auth.request.LoginRequest;
import nvh.run.ideaswap.data.dto.auth.request.LogoutRequest;
import nvh.run.ideaswap.data.dto.auth.request.RefreshTokenRequest;
import nvh.run.ideaswap.data.dto.auth.request.RegisterRequest;
import nvh.run.ideaswap.data.dto.auth.response.LoginResponse;
import nvh.run.ideaswap.data.dto.auth.response.RefreshTokenResponse;
import nvh.run.ideaswap.data.dto.auth.response.RegisterResponse;
import nvh.run.ideaswap.data.dto.auth.response.UserProfileResponse;
import nvh.run.ideaswap.data.entity.Role;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.IUserRepository;
import nvh.run.ideaswap.exceptions.DatabaseException;
import nvh.run.ideaswap.security.jwt.JwtUtilities;
import nvh.run.ideaswap.security.service.UserDetailsExtImpl;
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

import java.util.Collections;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    IUserRepository iUserRepository;
    RoleService roleService;
    JwtUtilities jwtUtilities;
    AuthenticationManager authenticationManager;
    private final UserService userService;

    public RegisterResponse register(RegisterRequest registerRequest) {
            User user=userService.createUser(registerRequest);
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

            User user = userService.findByUsername(authentication.getName());
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
            User user = iUserRepository.findByUsername(username)
                    .orElseThrow(() -> new DatabaseException("User not found"));

            Role role = roleService.getRoleById(user.getRoleID());
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
