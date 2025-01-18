package nvh.run.ideaswap.common.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nvh.run.ideaswap.api.service.intf.IUserService;
import nvh.run.ideaswap.common.validator.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private IUserService iUserService;

    @Override
    public void initialize(UniqueEmail constraint) {
        // Thực hiện khởi tạo nếu cần
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true; // Nếu email là null thì không kiểm tra, sẽ bị kiểm tra bởi @NotNull nếu cần
        }
        return !iUserService.existsByEmail(email);
    }
}
