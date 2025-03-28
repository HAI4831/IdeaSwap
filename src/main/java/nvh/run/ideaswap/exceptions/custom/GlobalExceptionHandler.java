package nvh.run.ideaswap.exceptions.custom;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.exceptions.DatabaseException;
import nvh.run.ideaswap.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static nvh.run.ideaswap.utils.ExceptionUtils.getAllCauseMessages;
import static nvh.run.ideaswap.utils.ExceptionUtils.getRootCause;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException e) {
        log.warn("Runtime exception occurred: {}", e.getMessage(), e);

        return createResponseError(e,HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> createResponseError(Exception e, HttpStatus status) {
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
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setStackTrace(formattedStackTrace);
//        errorResponse.setStackTrace(e.getStackTrace());
        errorResponse.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Object> handleDatabaseException(DatabaseException e) {
        log.error("DatabaseException occurred: {}", e.getMessage(), e);
        return createResponseError(e,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Illegal argument exception occurred: {}", e.getMessage(), e);
        return createResponseError(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("ConstraintViolationException occurred: {}", e.getMessage(), e);
        String errorMessages = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return createResponseError(e,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        log.warn("MethodArgumentNotValidException occurred: {}", ex.getMessage(), ex);

        super.handleMethodArgumentNotValid(ex, headers, status, request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .success(false)
                        .message(ex.getMessage())
                        .error(ex.getCause().getMessage())
                        .path(ex.getClass().getName())
                        .errorClass(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("ResourceNotFoundException occurred: {}", e.getMessage(), e);
        return createResponseError(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        log.error("Unhandled exception occurred: {}", e.getMessage(), e);
        return createResponseError(e,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}