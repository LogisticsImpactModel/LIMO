package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.Null;

public class NullValidator implements FieldValidator<Null> {

    @Override
    public void validate(Null n, Field field, Object value) throws ValidationException {
        if (value != null) {
            throw new ValidationException(field.getName() + " should be empty.");
        }
    }
    
}
