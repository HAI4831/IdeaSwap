package nvh.run.ideaswap.data.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.entity.Users;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse {
    private Users user;
    private Managers manager;
    @Builder.Default
    private boolean authenticated=false;
}
