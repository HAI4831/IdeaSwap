package nvh.run.ideaswap.data.dto.auth.response;

import lombok.Builder;

@Builder
public class LoginResponse {
    private boolean success;
    private String message;
    private String accessToken;
    private String refreshToken;
}
