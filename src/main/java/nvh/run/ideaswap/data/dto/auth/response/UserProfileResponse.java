package nvh.run.ideaswap.data.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.entity.Users;

import java.io.Serializable;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileResponse implements Serializable {
    private Users user;
    @Builder.Default
    private boolean authenticated=false;
}
