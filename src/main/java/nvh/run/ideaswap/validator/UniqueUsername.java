package nvh.run.ideaswap.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nvh.run.ideaswap.validator.impl.UniqueUsernameValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUsernameValidator.class) // Chỉ định validator
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUsername {
    String message() default "Username đã tồn tại"; // Thông báo mặc định
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
