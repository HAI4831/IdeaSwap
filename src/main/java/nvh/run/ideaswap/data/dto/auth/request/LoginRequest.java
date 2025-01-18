package nvh.run.ideaswap.data.dto.auth.request;

import lombok.Getter;
import nvh.run.ideaswap.common.validator.UniqueUsername;

@Getter
public class LoginRequest {
    @UniqueUsername
    private String username;
    private String password;
}
