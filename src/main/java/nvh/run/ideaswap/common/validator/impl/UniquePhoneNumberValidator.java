package nvh.run.ideaswap.common.validator.impl;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nvh.run.ideaswap.service.UserService;
import nvh.run.ideaswap.common.validator.UniquePhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    @Autowired
    private UserService iUserService;

    @Override
    public void initialize(UniquePhoneNumber constraint) {
        // Thực hiện khởi tạo nếu cần
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return true; // Nếu email là null thì không kiểm tra, sẽ bị kiểm tra bởi @NotNull nếu cần
        }
        return !iUserService.existsByPhoneNumber(phoneNumber);
    }
}

