package nvh.run.ideaswap.exceptions.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> allErrorMessages;
    @Builder.Default
    String error="Something went err";
    private String errorClass;
    private String path;
    private LocalDateTime timestamp;
    List<String> stackTrace;
//    StackTraceElement[] stackTrace;
//    @Getter(AccessLevel.NONE)
//    StackTraceElement[] stackTrace;
//    @JsonGetter("stackTrace")// thể hiện mã hóa json gọi getter của stackTrace nên gọi getFormattedStackTrace() thay vì mặc định
//    public String getFormattedStackTrace() {
//        if (stackTrace == null) return "";
//
//        return Arrays.stream(stackTrace)
//                .map(ste -> "    at " + ste.getClassName() + "." + ste.getMethodName() +
//                        " (" + ste.getFileName() + ":" + ste.getLineNumber() + ")")
//                .collect(Collectors.joining("\n"));
//    }
}

