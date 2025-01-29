package nvh.run.ideaswap.common.exceptions.exception.global;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status=400;
    private boolean success=false;
    private String message="Something went wrong";
    String error="Something went err";
    private String errorClass;
    private String path;
//    private String exceptionDetails;
    private LocalDateTime timestamp;
//    Throwable error;
    StackTraceElement[] stackTrace;
}

