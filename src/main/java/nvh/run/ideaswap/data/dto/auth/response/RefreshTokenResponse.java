package nvh.run.ideaswap.data.dto.auth.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RefreshTokenResponse {
    private String accessToken;
    private boolean tokenIsValid;
}
