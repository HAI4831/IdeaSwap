package nvh.run.ideaswap.exceptions.custom;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Primary
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        // Gọi xử lý gốc từ lớp cha
        ResponseEntity<Object> originalResponse = super.handleHttpMessageNotReadable(ex, headers, status, request);
        // Log lỗi chi tiết (nếu cần)
        log.error("JSON parse error: " + ex.getMessage());
        // Trả về thông báo lỗi rõ ràng cho client
        String errorMessage = "Invalid input: " + ex.getCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .success(false)
                        .message(ex.getMessage())
                        .error(ex.getCause().getMessage())
                        .path(ex.getClass().getName())
                        .build()
        );
    }
}
