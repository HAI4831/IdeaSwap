package nvh.run.ideaswap.common.exceptions.exception.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.common.exceptions.exception.custom.auth.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Object> handleDatabaseException(DatabaseException ex) {
        log.error("DatabaseException occurred: {}", ex.getMessage(), ex);
        return createResponseError(
                ex.getMessage(),
                DatabaseException.class.getName(),
                ex.getCause(),
                ex.getStackTrace(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<Object> createResponseError(String message, String errorClass, Throwable cause, StackTraceElement[] stackTrace, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setSuccess(false);
        errorResponse.setMessage(message);
        errorResponse.setErrorClass(errorClass);
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setStatus(status.value());
        errorResponse.setError(cause);
        errorResponse.setStackTrace(stackTrace);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        log.warn("Runtime exception occurred: {}", e.getMessage(), e);
        return createResponseError(
                e.getMessage(),
                e.getClass().getName(),
                e.getCause(),
                e.getStackTrace(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Illegal argument exception occurred: {}", e.getMessage(), e);
        return createResponseError(
                e.getMessage(),
                e.getClass().getName(),
                e.getCause(),
                e.getStackTrace(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("ConstraintViolationException occurred: {}", ex.getMessage(), ex);
        String errorMessages = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return createResponseError(
                errorMessages,
                ConstraintViolationException.class.getName(),
                ex.getCause(),
                ex.getStackTrace(),
                HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.warn("MethodArgumentNotValidException occurred: {}", ex.getMessage(), ex);

        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return createResponseError(
                "Validation failed",
                MethodArgumentNotValidException.class.getName(),
                null,
                ex.getStackTrace(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("ResourceNotFoundException occurred: {}", ex.getMessage(), ex);
        return createResponseError(
                ex.getMessage(),
                ResourceNotFoundException.class.getName(),
                null,
                ex.getStackTrace(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);
        return createResponseError(
                "Internal server error",
                ex.getClass().getName(),
                ex.getCause(),
                ex.getStackTrace(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
