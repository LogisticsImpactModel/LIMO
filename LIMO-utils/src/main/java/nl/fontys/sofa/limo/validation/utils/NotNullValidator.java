package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.NotNull;

public class NotNullValidator implements FieldValidator<NotNull> {

    @Override
    public void validate(NotNull n, Field field, Object value) throws ValidationException {
        if (value == null) {
            throw new ValidationException(field.getName() + " should not be empty.");
        }
    }
}
