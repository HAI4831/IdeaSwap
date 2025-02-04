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
    @Builder.Default
    private int status=400;
    @Builder.Default
    private boolean success=false;
    @Builder.Default
    private String message="Something went wrong";
    @Builder.Default
    String error="Something went err";
    private String errorClass;
    private String path;
//    private String exceptionDetails;
    private LocalDateTime timestamp;
//    Throwable error;
    StackTraceElement[] stackTrace;
}

