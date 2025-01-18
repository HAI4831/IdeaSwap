package nvh.run.authsystemgradle.data.dto.auth.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {
    private String username;
    private String password;
}
