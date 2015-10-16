package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.AssertTrue;

public class AssertTrueValidator implements FieldValidator<AssertTrue> {

    @Override
    public void validate(AssertTrue assertTrue, Field field, Object value) throws ValidationException {
        
        if (value instanceof Boolean) {
            if (!((Boolean)value)) {
                throw new ValidationException(field.getName() + " should be true.");
            }
        }
    }
    
}
