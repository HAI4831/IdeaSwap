package nvh.run.ideaswap.data.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.data.entity.Manager;
import nvh.run.ideaswap.data.entity.User;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@JsonRootName("registerResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RegisterResponse {
    private User user;
    private Manager manager;
}
