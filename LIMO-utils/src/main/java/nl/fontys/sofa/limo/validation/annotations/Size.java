package nl.fontys.sofa.limo.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The size of the field or property is evaluated and must match the specified 
 * boundaries. If the field or property is a <code>String</code>, the size of 
 * the string is evaluated. If the field or property is a 
 * <code>Collection</code>, the size of the <code>Collection</code> is 
 * evaluated. If the field or property is a <code>Map</code>, the size of the 
 * <code>Map</code> is evaluated. If the field or property is an array, the size 
 * of the array is evaluated. Use one of the optional <code>max</code> or 
 * <code>min</code> elements to specify the boundaries.
 * 
 * @author Miguel Gonzalez <m.gonzalez@student.fontys.nl>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
}
