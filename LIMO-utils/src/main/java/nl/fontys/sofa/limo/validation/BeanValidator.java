package nl.fontys.sofa.limo.validation;

import java.lang.reflect.Field;

/**
 * Validates beans according to their annotations and state.
 * 
 * @author Miguel Gonzalez <m.gonzalez@student.fontys.nl>
 */
public class BeanValidator {
    
    private static final BeanValidator instance = new BeanValidator();
    
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
            System.out.println(field.getName());
        }
        // for each field
        // -> check annotation type (if type is not fitting show logger warning)
        // -> check current value and throw exception if necessary
    }
}
