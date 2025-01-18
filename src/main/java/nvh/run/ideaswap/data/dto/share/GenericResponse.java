package nvh.run.authsystemgradle.data.dto.share;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class GenericResponse {
    public ResponseEntity<Object> createResponse(boolean success, String message, Object entity, String error) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", message);

        if (success) {
            response.put("entity", entity);
        } else {
            response.put("error", error);
        }

        if (success) {
            return ResponseEntity.status(200).body(response);
        } else {
            int statusCode = error.equals("Invalid or expired refresh token") ? 401 : 500;
            return ResponseEntity.status(statusCode).body(response);
        }
    }

}
