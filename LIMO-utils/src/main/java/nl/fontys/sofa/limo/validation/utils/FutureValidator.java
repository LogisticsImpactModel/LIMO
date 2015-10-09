package nl.fontys.sofa.limo.validation.utils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.validation.annotations.Future;

public class FutureValidator implements FieldValidator<Future> {

    @Override
    public void validate(Future past, Field field, Object value) throws ValidationException {
        if (value instanceof Date) {
            Date d = (Date)value;
            LocalDate l = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (!l.isAfter(LocalDate.now())) {
                throw new ValidationException(field.getName() + " should have a date in the future.");
            }
        }
    }
    
}
