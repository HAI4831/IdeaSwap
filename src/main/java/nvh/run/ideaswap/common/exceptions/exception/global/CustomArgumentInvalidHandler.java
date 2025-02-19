//package nvh.run.ideaswap.common.exceptions.exception.global;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.MessageSource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindException;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import java.time.LocalDateTime;
//import java.util.Locale;
//
//@ControllerAdvice
//public class CustomArgumentInvalidHandler {
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
//    public ResponseEntity<?> handleMethodArgumentNotValid(BindException ex, Locale locale, MessageSource messageSource){
//
//        final ObjectError error = ex.getBindingResult().getAllErrors().get(0);
//        final String message = messageSource.getMessage(error,locale);
////        logger.log(level.SEVERE, ex.getMessage(),ex);
//        log.warn("CustomArgumentInvalidHandler:"+message);
//        return ResponseEntity.badRequest().body(
//                ErrorResponse.builder()
//                        .success(false)
//                        .message(message)
//                        .errorClass(this.getClass().getSimpleName())
//                        .timestamp(LocalDateTime.now())
//                        .error(error.getDefaultMessage())
//                        .path("/api/v1/role/add RoleController.createRole")
//                        .stackTrace(ex.getStackTrace())
//                        .build()
//        );
//    }
//}
