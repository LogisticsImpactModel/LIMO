package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.Pattern;

public class PatternValidator implements FieldValidator<Pattern> {

    @Override
    public void validate(Pattern pattern, Field field, Object value) throws ValidationException {
        if (value == null) {
            throw new ValidationException(field.getName() + " should not be empty.");
        } else if (value instanceof String) {
            String text = (String)value;
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern.regexp());
            if (!p.matcher(text).find()) {
                throw new ValidationException(field.getName() + " is not allowed.");
            }
        }
    }
    
}
