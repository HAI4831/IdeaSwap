package nvh.run.ideaswap.common.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import nvh.run.ideaswap.common.validator.IsObjectID;
import org.bson.types.ObjectId;

import java.util.Collection;

public class ObjectIDValidator implements ConstraintValidator<IsObjectID, Object> {

    @Override
    public void initialize(IsObjectID constraint) {
        // Thực hiện khởi tạo nếu cần
    }

    @Override
    public boolean isValid(Object ids, ConstraintValidatorContext context) {
        if (ids == null) {
            return true; // null là hợp lệ
        }

        if (ids instanceof String) {
            return isValidObjectID((String) ids);
        } else if (ids instanceof Collection) {
            return isValidObjectIDList((Collection<String>) ids);
        }
        return false;
    }
    protected boolean isValidObjectID(String id) {
        if (id == null || id.isEmpty()) {
            return true;
        }
        try {
            new ObjectId(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    protected boolean isValidObjectIDList(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return true;
        }

        for (String id : ids) {
            if (id == null || id.isEmpty()) {
                continue;
            }
            try {
                new ObjectId(id);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return true;
    }
}
