package nl.fontys.sofa.limo.domain.exceptions;

/**
 *
 * @author Matthias Brück
 */
public class SupplyChainException extends Exception {

    /**
     * Creates a new instance of <code>LogicalOperationException</code> without
     * detail message.
     */
    public SupplyChainException() {
    }

    /**
     * Constructs an instance of <code>LogicalOperationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SupplyChainException(String msg) {
        super(msg);
    }
}
