package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.DecimalMax;

public class DecimalMaxValidator implements FieldValidator<DecimalMax> {

    @Override
    public void validate(DecimalMax max, Field field, Object value) throws ValidationException {
        double val = Double.valueOf(max.value());
        if (value instanceof BigDecimal) {
            if (((BigDecimal)value).compareTo(BigDecimal.valueOf(val)) > 0) {
                throw new ValidationException(field.getName() + " value should be at maximum " + val);
            }
        } else if (value instanceof Float) {
            if ((Float) value > val) {
                throw new ValidationException(field.getName() + " value should be at maximum " + val);
            }
        } else if (value instanceof Double) {
            if ((Double) value > val) {
                throw new ValidationException(field.getName() + " value should be at maximum " + val);
            }
        }
    }
    
}
