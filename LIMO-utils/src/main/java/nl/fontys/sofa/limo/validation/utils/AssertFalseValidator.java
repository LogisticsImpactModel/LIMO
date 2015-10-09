package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.AssertFalse;

public class AssertFalseValidator implements FieldValidator<AssertFalse> {

    @Override
    public void validate(AssertFalse assertFalse, Field field, Object value) throws ValidationException {
        
        if (value instanceof Boolean) {
            if (((Boolean)value)) {
                throw new ValidationException(field.getName() + " should be false.");
            }
        }
    }
    
}
