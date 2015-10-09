package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.Past;

public class PastValidator implements FieldValidator<Past> {

    @Override
    public void validate(Past past, Field field, Object value) throws ValidationException {
        if (value instanceof Date) {
            Date d = (Date)value;
            LocalDate l = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!l.isBefore(LocalDate.now())) {
                throw new ValidationException(field.getName() + " should have a date in the past");
            }
        }
    }
    
}
