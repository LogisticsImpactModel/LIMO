package nl.fontys.sofa.limo.view.chain;

import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * ChainBuilder defines method for building a supplychain.
 *
 * @author Sebastiaan Heijmann
 */
public interface ChainBuilder {

    /**
     * Add a hub to the supplychain.
     *
     * @param hub - the hub to be added.
     */
    void addHub(Hub hub);

    /**
     * Get the number of hubs in the chain.
     *
     * @return int - the number of hubs in the chain.
     */
    int getNumberOfHubs();

    /**
     * Get the starthub of the supply chain.
     *
     * @return hub - the start hub.
     */
    Hub getStartHub();

    /**
     * Set the start hub of the chain.
     *
     * @param hub - the starting hub.
     */
    void setStartHub(Hub hub);

    /**
     * Connect two hubs by a leg.
     *
     * @param source - the source hub.
     * @param connection - the connecting leg.
     * @param target - the target hub.
     */
    void connectHubsByLeg(Hub source, Leg connection, Hub target);

    /**
     * Validate the supply chain.
     *
     * @return - true if validation succeeds.
     */
    boolean validate();

}
