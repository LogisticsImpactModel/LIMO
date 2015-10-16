package nl.fontys.sofa.limo.validation.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import nl.fontys.sofa.limo.validation.ValidationException;

/**
 * Validates a field against a certain annotation
 * 
 * @author Miguel Gonzalez <m.gonzalez@student.fontys.nl>
 */
public interface FieldValidator<T extends Annotation> {
    
    void validate(T annotation, Field field, Object value) throws ValidationException;
}
