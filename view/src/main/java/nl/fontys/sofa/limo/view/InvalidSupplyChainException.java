package nl.fontys.sofa.limo.view;

import java.io.IOException;

/**
 * This exception is thrown when trying to save an invalid supply chain. An
 * invalid supply chain cannot be saved due to technical limitations. The supply
 * chains are based on LinkedLists, when no next() object is found, the supply
 * chain is finished. Therefore an invalid supply chain cannot be saved
 *
 * @author Mike de Roode
 */
public class InvalidSupplyChainException extends IOException {

    public InvalidSupplyChainException() {
    }

    public InvalidSupplyChainException(String message) {
        super(message);
    }

}
