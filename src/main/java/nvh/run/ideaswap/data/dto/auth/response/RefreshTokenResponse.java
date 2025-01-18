package nvh.run.authsystemgradle.data.dto.auth.response;

import lombok.Builder;

@Builder
public class RefreshTokenResponse {
    private boolean success;
    private String message;
    private String accessToken;
}
