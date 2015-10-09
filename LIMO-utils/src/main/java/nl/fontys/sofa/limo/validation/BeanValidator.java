package nl.fontys.sofa.limo.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import nl.fontys.sofa.limo.validation.annotations.AssertFalse;
import nl.fontys.sofa.limo.validation.annotations.AssertTrue;
import nl.fontys.sofa.limo.validation.annotations.DecimalMax;
import nl.fontys.sofa.limo.validation.annotations.DecimalMin;
import nl.fontys.sofa.limo.validation.annotations.Max;
import nl.fontys.sofa.limo.validation.annotations.Min;
import nl.fontys.sofa.limo.validation.annotations.NotNull;
import nl.fontys.sofa.limo.validation.annotations.Null;
import nl.fontys.sofa.limo.validation.annotations.Pattern;
import nl.fontys.sofa.limo.validation.utils.AssertFalseValidator;
import nl.fontys.sofa.limo.validation.utils.AssertTrueValidator;
import nl.fontys.sofa.limo.validation.utils.DecimalMaxValidator;
import nl.fontys.sofa.limo.validation.utils.DecimalMinValidator;
import nl.fontys.sofa.limo.validation.utils.FieldValidator;
import nl.fontys.sofa.limo.validation.utils.MaxValidator;
import nl.fontys.sofa.limo.validation.utils.MinValidator;
import nl.fontys.sofa.limo.validation.utils.NotNullValidator;
import nl.fontys.sofa.limo.validation.utils.NullValidator;
import nl.fontys.sofa.limo.validation.utils.PatternValidator;

/**
 * Validates beans according to their annotations and state.
 * 
 * @author Miguel Gonzalez <m.gonzalez@student.fontys.nl>
 */
public class BeanValidator {
    
    private static final BeanValidator instance = new BeanValidator();
    
    private static Map<Class<? extends Annotation>, FieldValidator<?>> validators = new HashMap<>();

    static {
        validators.put(Min.class, new MinValidator());
        validators.put(Max.class, new MaxValidator());
        validators.put(AssertTrue.class, new AssertTrueValidator());
        validators.put(AssertFalse.class, new AssertFalseValidator());
        validators.put(DecimalMin.class, new DecimalMinValidator());
        validators.put(DecimalMax.class, new DecimalMaxValidator());
        validators.put(Pattern.class, new PatternValidator());
        validators.put(Null.class, new NullValidator());
        validators.put(NotNull.class, new NotNullValidator());
    }
    
    private BeanValidator() { }
    
    public static BeanValidator getInstance() {
        return instance;
    }
    
    /**
     * Validates beans and throws exception on bad result.
     * 
     * @param bean bean to validate
     * @throws ValidationException is thrown on bad validation
     */
    public <T> void validate(T bean) throws ValidationException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            for (Annotation annotation : field.getAnnotations()) {
                try {
                    FieldValidator validator = validators.get(annotation.annotationType());
                    if (validator != null) {
                        validator.validate(annotation, field, field.get(bean));
                    }
                } catch (IllegalArgumentException ex) {
                    
                } catch (IllegalAccessException ex) {
                    
                }
            }
        }
    }
}
