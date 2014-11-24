package nl.fontys.sofa.limo.api.exception;

/**
 * Exception which can be thrown if a certain DAO is not found.
 *
 * @author Sebastiaan Heijmann
 */
public class DAONotFoundException extends Exception{

	public DAONotFoundException() {
	}

	public DAONotFoundException(String message) {
		super(message);
	}
}
