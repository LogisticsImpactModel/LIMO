package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.Max;
import nl.fontys.sofa.limo.validation.annotations.Min;

public class MaxValidator implements FieldValidator<Max> {

    @Override
    public void validate(Max max, Field field, Object value) throws ValidationException {
        
        if (value instanceof Long) {
            if ((Long)value > max.value()) {
                throw new ValidationException(field.getName() + " should at maximum " + max.value());
            }
        } else if (value instanceof Short) {
            if ((Short)value > max.value()) {
                throw new ValidationException(field.getName() + " should at maximum " + max.value());
            }
        } if (value instanceof Integer) {
            if ((Integer)value > max.value()) {
                throw new ValidationException(field.getName() + " should at maximum " + max.value());
            }
        }
    }
    
}
