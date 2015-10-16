package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.Min;

public class MinValidator implements FieldValidator<Min> {

    @Override
    public void validate(Min min, Field field, Object value) throws ValidationException {
        
        if (value instanceof Long) {
            if ((Long)value < min.value()) {
                throw new ValidationException(field.getName() + " should not be at least " + min.value());
            }
        } else if (value instanceof Short) {
            if ((Short)value < min.value()) {
                throw new ValidationException(field.getName() + " should not be at least " + min.value());
            }
        } if (value instanceof Integer) {
            if ((Integer)value < min.value()) {
                throw new ValidationException(field.getName() + " should not be at least " + min.value());
            }
        }
    }
    
}
