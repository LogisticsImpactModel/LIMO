package nl.fontys.sofa.limo.validation;

/**
 * Is thrown on bad validation.
 * 
 * @author Miguel Gonzalez <m.gonzalez@student.fontys.nl>
 */
public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
