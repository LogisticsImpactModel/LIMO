package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.Size;

public class SizeValidator implements FieldValidator<Size> {

    @Override
    public void validate(Size size, Field field, Object value) throws ValidationException {
        int min = size.min();
        int max = size.max();
        if (value instanceof String) {
            validateSize(min, max, ((String)value).length());
        } else if (value instanceof Map) {
            validateSize(min, max, ((Map)value).size());
        } else if (value instanceof Collection) {
            validateSize(min, max, ((Collection)value).size());
        } else if (value instanceof Object[]) {
            Object arr[] = (Object[]) value;
            validateSize(min, max, arr.length);
        }
    }
    
    private void validateSize(int min, int max, int actual) throws ValidationException {
        if (actual < min || actual > max) {
            throw new ValidationException(actual + " should be between " + min + " and " + max);
        }
    }
    
}
