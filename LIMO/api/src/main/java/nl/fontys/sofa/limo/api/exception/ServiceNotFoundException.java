package nl.fontys.sofa.limo.api.exception;

/**
 * Exception which can be thrown if a certain Service is not found.
 *
 * @author Sebastiaan Heijmann
 */
public class ServiceNotFoundException extends Exception{

	public ServiceNotFoundException() {
	}

	public ServiceNotFoundException(String message) {
		super(message);
	}

}
