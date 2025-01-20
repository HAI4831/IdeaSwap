package nvh.run.ideaswap.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nvh.run.ideaswap.common.validator.impl.UniqueEmailValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class) // Chỉ định validator
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhoneNumber {
    String message() default "Số điện thoại đã tồn tại"; // Thông báo mặc định
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
