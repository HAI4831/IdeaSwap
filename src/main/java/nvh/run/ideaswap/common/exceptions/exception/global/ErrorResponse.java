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
    private LocalDateTime timestamp;
    private boolean success;
    private String message;
    private String errorClass;
    private String path;
    private int status;
    private String exceptionDetails;
    Throwable cause;
    StackTraceElement[] stackTrace;
}

