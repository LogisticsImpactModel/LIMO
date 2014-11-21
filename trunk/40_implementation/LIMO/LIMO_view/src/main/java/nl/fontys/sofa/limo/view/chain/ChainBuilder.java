package nl.fontys.sofa.limo.view.chain;

import nl.fontys.sofa.limo.domain.component.hub.Hub;

/**
 * ChainBuilder defines method for building a supplychain.
 *
 * @author Sebastiaan Heijmann
 */
public interface ChainBuilder {

    void addHub(Hub hub);

}
