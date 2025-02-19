package nvh.run.ideaswap.api.controller;

import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.data.dto.auth.request.LoginRequest;
import nvh.run.ideaswap.data.dto.auth.request.LogoutRequest;
import nvh.run.ideaswap.data.dto.auth.request.RefreshTokenRequest;
import nvh.run.ideaswap.data.dto.auth.request.RegisterRequest;
import nvh.run.ideaswap.data.dto.auth.response.LoginResponse;
import nvh.run.ideaswap.data.dto.auth.response.ManagerProfileResponse;
import nvh.run.ideaswap.data.dto.auth.response.RefreshTokenResponse;
import nvh.run.ideaswap.service.AuthManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/auth")
public class AuthManagerController {
    private final AuthManagerService authManagerService;

    public AuthManagerController(AuthManagerService authManagerService) {
        this.authManagerService = authManagerService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "User registered successfully",
                        "user", authManagerService.register(registerRequest).getManager()
                )
        );
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authManagerService.login(loginRequest);
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Login successful!",
                        "accessToken", loginResponse.getAccessToken(),
                        "refreshToken", loginResponse.getRefreshToken()
                )
        );
    }
    @GetMapping("/account")
    public ResponseEntity<Object> getUserProfile() {
        log.info("Before AuthManagerController.getUserProfile was called");
        ManagerProfileResponse managerProfileResponse = authManagerService.getManagerProfile();
        log.info("After AuthManagerController.getUserProfile was called");
        if( !managerProfileResponse.isAuthenticated()){
            return ResponseEntity.status(401).body(
                    Map.of(
                            "success", false,
                            "message", "Manager not authenticated"
                    )
            );
        }
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Manager Profile successfully",
                        "user", managerProfileResponse.getManager()
                )
        );
    }
    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenResponse refreshTokenResponse  = authManagerService.refreshToken(refreshTokenRequest);
        if(!refreshTokenResponse.isTokenIsValid()){
            return ResponseEntity.status(401).body(
                    Map.of(
                            "success", false,
                            "message", "Invalid or expired refresh token"
                    )
            );
        }
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Token refreshed successfully",
                        "accessToken", refreshTokenResponse.getAccessToken()
                )
        );
    }
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(
            @RequestBody LogoutRequest request
//            @RequestHeader("Authorization") String token
    ) {
        return authManagerService.logout(request);
    }
}
