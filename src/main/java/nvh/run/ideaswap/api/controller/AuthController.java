package nvh.run.ideaswap.api.controller;


import nvh.run.ideaswap.data.dto.auth.request.LoginRequest;
import nvh.run.ideaswap.data.dto.auth.request.LogoutRequest;
import nvh.run.ideaswap.data.dto.auth.request.RefreshTokenRequest;
import nvh.run.ideaswap.data.dto.auth.request.RegisterRequest;
import nvh.run.ideaswap.data.dto.auth.response.LoginResponse;
import nvh.run.ideaswap.data.dto.auth.response.RefreshTokenResponse;
import nvh.run.ideaswap.data.dto.auth.response.UserProfileResponse;
import nvh.run.ideaswap.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "User registered successfully",
                        "user", authService.register(registerRequest).getUser()
                )
        );
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
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
        UserProfileResponse profileResponse = authService.getUserProfile();
        if( !profileResponse.isAuthenticated()){
            return ResponseEntity.status(401).body(
                    Map.of(
                            "success", false,
                            "message", "User not authenticated"
                    )
            );
        }
        return ResponseEntity.ok(
                        Map.of(
                                "success", true,
                                "message", "Retrieve User Profile successfully",
                                "user", profileResponse.getUser()
                        )
                );
    }
    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        RefreshTokenResponse refreshTokenResponse  = authService.refreshToken(refreshTokenRequest);
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
        return authService.logout(request);
    }
}
