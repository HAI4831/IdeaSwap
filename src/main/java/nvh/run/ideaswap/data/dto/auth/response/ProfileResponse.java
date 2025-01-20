package nvh.run.ideaswap.data.dto.auth.response;

import lombok.Builder;
import lombok.Getter;
import nvh.run.ideaswap.data.entity.Users;

@Builder
@Getter
public class ProfileResponse {
    private Users user;
    private boolean authenticated=false;
}
