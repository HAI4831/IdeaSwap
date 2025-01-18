package nvh.run.ideaswap.api.service;


import nvh.run.ideaswap.data.dto.auth.request.LoginRequest;
import nvh.run.ideaswap.data.dto.auth.request.LogoutRequest;
import nvh.run.ideaswap.data.dto.auth.request.RefreshTokenRequest;
import nvh.run.ideaswap.data.dto.auth.request.RegisterRequest;
import nvh.run.ideaswap.data.dto.share.ApiResponse;
import nvh.run.ideaswap.data.entity.Users;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<Object> register(RegisterRequest registerRequest);
    ResponseEntity<ApiResponse<Users>> registerApi(RegisterRequest registerRequest);
    ResponseEntity<Object> login(LoginRequest loginRequest);
    ResponseEntity<Object> getUserProfile();
    ResponseEntity<Object> refreshToken(RefreshTokenRequest refreshToken);
    ResponseEntity<Object> logout(LogoutRequest request);
}
