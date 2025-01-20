package nvh.run.ideaswap.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import nvh.run.ideaswap.common.validator.impl.ObjectIDValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ObjectIDValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsObjectID {
    String message() default "Dữ liệu ID cần phải là kiểu định dạng hexadecimal";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
