package nl.fontys.sofa.limo.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The value of the field or property must be a number within a specified range. 
 * The integer element specifies the maximum integral digits for the number, and 
 * the fraction element specifies the maximum fractional digits for the number.
 * 
 * @author Miguel Gonzalez <m.gonzalez@student.fontys.nl>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Digits {
    int integer();
    int fraction();
}
