package nl.fontys.sofa.limo.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The value of the field or property must be a date in the past.
 * 
 * @author Miguel Gonzalez <m.gonzalez@student.fontys.nl>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Past {
}
