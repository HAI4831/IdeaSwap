package nvh.run.ideaswap.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nvh.run.ideaswap.service.UserService;
import nvh.run.ideaswap.validator.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private UserService iUserService;

    @Override
    public void initialize(UniqueUsername constraint) {
        // Thực hiện khởi tạo nếu cần
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return true; // Nếu email là null thì không kiểm tra, sẽ bị kiểm tra bởi @NotNull nếu cần
        }
        return !iUserService.existsByUsername(username);
    }
}

