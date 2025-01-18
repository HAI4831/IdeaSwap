package nvh.run.ideaswap.data.dto.auth.response;

import lombok.Builder;

@Builder
public class RegisterResponse {
    private boolean success;
    private String message;
    private String error;
}
