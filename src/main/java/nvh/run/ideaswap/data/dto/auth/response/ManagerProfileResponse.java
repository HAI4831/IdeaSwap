package nvh.run.ideaswap.data.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import nvh.run.ideaswap.data.entity.Manager;

import java.io.Serializable;
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagerProfileResponse implements Serializable {
    private Manager manager;
    @Builder.Default
    private boolean authenticated=false;
}

