package nvh.run.authsystemgradle.data.dto.auth.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshTokenRequest {
    private String refreshToken;
}
