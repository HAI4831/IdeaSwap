package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.auth.request.LoginRequest;
import nvh.run.ideaswap.data.dto.auth.request.LogoutRequest;
import nvh.run.ideaswap.data.dto.auth.request.RefreshTokenRequest;
import nvh.run.ideaswap.data.dto.auth.request.RegisterRequest;
import nvh.run.ideaswap.data.dto.auth.response.LoginResponse;
import nvh.run.ideaswap.data.dto.auth.response.ManagerProfileResponse;
import nvh.run.ideaswap.data.dto.auth.response.RefreshTokenResponse;
import nvh.run.ideaswap.data.dto.auth.response.RegisterResponse;
import nvh.run.ideaswap.data.entity.Gender;
import nvh.run.ideaswap.data.entity.Manager;
import nvh.run.ideaswap.data.entity.Role;
import nvh.run.ideaswap.data.repository.ManagerRepository;
import nvh.run.ideaswap.exceptions.DatabaseException;
import nvh.run.ideaswap.security.jwt.JwtUtilities;
import nvh.run.ideaswap.security.service.ManagerDetailsExtImpl;
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

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthManagerService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    ManagerRepository managerRepository;
    RoleService roleService;
    PasswordEncoder passwordEncoder;
    JwtUtilities jwtUtilities;
    AuthenticationManager authenticationManager;
    private final ManagerService managerService;

    public RegisterResponse register(RegisterRequest registerRequest) {
        Role role = roleService.findByName("manager");
        Manager manager;
        try {
            if (managerRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
                throw new RuntimeException("Can't register new user because the user already exists.");
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while checking the user: " + e.getMessage(), e);
        }
        try {
            manager= managerRepository.save(
                    //map
                    Manager.builder()
                            .username(registerRequest.getUsername())
                            .password(registerRequest.getPassword() ==null ? passwordEncoder.encode("abCD@1234") : passwordEncoder.encode(registerRequest.getPassword()))  // Encode password
                            .firstName(registerRequest.getFirstName()==null ? "":registerRequest.getFirstName())
                            .lastName(registerRequest.getLastName()==null?"":registerRequest.getLastName())
                            .email(registerRequest.getEmail())
                            .roleID(role.getId())
                            .phoneNumber(registerRequest.getPhoneNumber() == null ? "" : registerRequest.getPhoneNumber() )
                            .address(registerRequest.getAddress() == null ? "Ninh Bình" : registerRequest.getAddress())
                            .gender(registerRequest.getGender() == null ? Gender.Male : registerRequest.getGender())
                            .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
                            .birthday(registerRequest.getBirthday() == null ? LocalDate.parse("01/01/1970") : LocalDate.from(registerRequest.getBirthday()))
                            .build());
        }catch (Exception e){
            throw new DatabaseException("Register failed for manager ", e);
        }
        return RegisterResponse.builder()
                .manager(manager)
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

    @Cacheable(value = "manager_profile_response")
    public ManagerProfileResponse getManagerProfile() {
        log.info("start AuthManagerService.getManagerProfile was called ");
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Manager manager = managerService.findManagerByUsername(authentication.getName());
            log.info("AuthManagerService.getManagerProfile was called with manager {}", manager);
            if(manager!=null)return ManagerProfileResponse.builder().manager(manager).authenticated(true).build();


            return ManagerProfileResponse.builder()
                    .manager(null)
                    .authenticated(false)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user profile",e);
        }
    }

    //            if (authentication != null && authentication.isAuthenticated()) {
//                ManagerDetailsExtImpl principal = (ManagerDetailsExtImpl)authentication.getPrincipal();
//                log.info("Principal: {}", principal.toString());
//                ManagerDetailsExtImpl manager = (ManagerDetailsExtImpl) principal;
//                return ProfileResponse.builder()
//                        .manager(manager)
//                        .authenticated(true)
//                        .build();
//            }
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
            Manager manager = managerRepository.findByUsername(username)
                    .orElseThrow(() -> new DatabaseException("User not found"));

            Role role = roleService.findByName("manager");
            // Create authentication object
            ManagerDetailsExtImpl userDetails = new ManagerDetailsExtImpl(manager, Collections.singleton(new SimpleGrantedAuthority(role.getName())));
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
