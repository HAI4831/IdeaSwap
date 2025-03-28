package nvh.run.ideaswap.utils;

import nvh.run.ideaswap.exceptions.custom.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ExceptionUtils {
    public static ResponseEntity<Object> createResponseError(Exception e, HttpStatus status) {
        // Lấy lỗi gốc (root cause)
        Throwable rootCause = getRootCause(e);
        String rootMessage = (rootCause != null) ? rootCause.getMessage() : "Unknown root cause";
        // Lấy danh sách tất cả lỗi từ gốc đến lỗi cuối
        List<String> allCauseMessages = getAllCauseMessages(e);

        List<String> formattedStackTrace = Arrays.stream(e.getStackTrace())
                .map(ste -> "    at " + ste.getClassName() + "." + ste.getMethodName() +
                        " (" + ste.getFileName() + ":" + ste.getLineNumber() + ")")
                .collect(Collectors.toList());


        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status.value());
        errorResponse.setSuccess(false);
        errorResponse.setMessage(rootMessage);
        errorResponse.setAllErrorMessages(allCauseMessages);
        errorResponse.setError(e.getCause().getMessage());
        errorResponse.setErrorClass(e.getClass().getName());
//        errorResponse.setPath(request.getRequestURI());
        errorResponse.setStackTrace(formattedStackTrace);
//        errorResponse.setStackTrace(e.getStackTrace());
        errorResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(status).body(errorResponse);
    }
    public static List<String> formattedStackTrace(Exception e) {
        List<String> formattedStackTrace = Arrays.stream(e.getStackTrace())
                .map(ste -> "    at " + ste.getClassName() + "." + ste.getMethodName() +
                        " (" + ste.getFileName() + ":" + ste.getLineNumber() + ")")
                .collect(Collectors.toList());
        return formattedStackTrace;
    }
    public static Map<String, Object> extractExceptionDetails(Throwable e) {
        Map<String, Object> errorDetails = new LinkedHashMap<>();
        List<String> allMessages = new ArrayList<>();
        List<String> allCauses = new ArrayList<>();
        List<String> stackTraces = new ArrayList<>();
        
        Throwable cause = e;
        while (cause != null) {
            allMessages.add(cause.getMessage());
            allCauses.add(cause.getClass().getName());
            stackTraces.add(getStackTraceAsString(cause));
            cause = cause.getCause();
        }
        
        errorDetails.put("exceptionType", e.getClass().getName());  // Loại Exception chính
        errorDetails.put("allMessages", allMessages);  // Danh sách tất cả thông điệp lỗi
        errorDetails.put("allCauses", allCauses);  // Danh sách tất cả nguyên nhân
        errorDetails.put("stackTraces", stackTraces);  // StackTrace của từng lỗi
        
        return errorDetails;
    }

    public static Throwable getRootCause(Throwable e) {
        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }
    public static String getRootCauseMessage(Throwable e) {
        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause.getMessage();
    }

    public static String getExceptionDetails(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception Type: ").append(e.getClass().getName()).append("\n");

        Throwable cause = e;
        int level = 0;
        while (cause != null) {
            sb.append("Cause [Level ").append(level).append("]: ")
                    .append(cause.getClass().getName())
                    .append(" - ").append(cause.getMessage())
                    .append("\n");
            cause = cause.getCause();
            level++;
        }

        sb.append("\nStack Trace:\n").append(getStackTraceAsString(e));

        return sb.toString();
    }

    public static String getStackTraceAsString(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
    public static List<String> getAllCauseMessages(Throwable e) {
        List<String> messages = new ArrayList<>();
        Throwable cause = e;
        while (cause != null) {
            messages.add(cause.getMessage());
            cause = cause.getCause();
        }
        return messages;
    }
}
