package nvh.run.authsystemgradle.api.controller;

import nvh.run.authsystemgradle.api.service.IAuthService;
import nvh.run.authsystemgradle.data.dto.auth.request.LoginRequest;
import nvh.run.authsystemgradle.data.dto.auth.request.LogoutRequest;
import nvh.run.authsystemgradle.data.dto.auth.request.RefreshTokenRequest;
import nvh.run.authsystemgradle.data.dto.auth.request.RegisterRequest;
import nvh.run.authsystemgradle.data.dto.share.ApiResponse;
import nvh.run.authsystemgradle.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
    @PostMapping("/registerApi")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<User>> registerApi(@RequestBody RegisterRequest registerRequest) {
        return authService.registerApi(registerRequest);
    }
    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "${UserController.signin}")
//    @ApiResponses(value = {//
//            @ApiResponse(code = 400, message = "Something went wrong"), //
//            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @GetMapping("/account")
    public ResponseEntity<Object> getUserProfile() {
        return authService.getUserProfile();
    }
    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(
            @RequestBody LogoutRequest request
//            @RequestHeader("Authorization") String token
    ) {
        return authService.logout(request);
    }
}
