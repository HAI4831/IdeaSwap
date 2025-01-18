package nvh.run.authsystemgradle.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nvh.run.authsystemgradle.common.validator.impl.UniqueEmailValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class) // Chỉ định validator
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email đã tồn tại"; // Thông báo mặc định
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
