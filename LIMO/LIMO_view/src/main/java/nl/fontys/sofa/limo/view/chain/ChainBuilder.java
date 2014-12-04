package nl.fontys.sofa.limo.view.chain;

import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * ChainBuilder defines method for building a
 * {@link nl.fontys.sofa.limo.domain.component.SupplyChain}. This class is also
 * responsible correctly updating and validating the
 * {@link nl.fontys.sofa.limo.domain.component.SupplyChain}.
 * <p>
 * A single ChainBuilder is mapped to a single
 * {@link nl.fontys.sofa.limo.domain.component.SupplyChain}.
 *
 * @author Sebastiaan Heijmann
 */
public interface ChainBuilder {

    /**
     * Add a hub to the supplychain.
     *
     * @param hub the hub to be added.
     */
    void addHub(Hub hub);

    /**
     * Remove the hub from the chain.
     *
     * @param hub the hub to be removed.
     */
    void removeHub(Hub hub);

    /**
     * Get the number of hubs in the chain.
     *
     * @return the number of hubs in the chain.
     */
    int getNumberOfHubs();

    /**
     * Get the starthub of the supply chain.
     *
     * @return the start hub.
     */
    Hub getStartHub();

    /**
     * Set the start hub of the chain.
     *
     * @param hub the starting hub.
     */
    void setStartHub(Hub hub);

    /**
     * Connect two hubs by a leg.
     *
     * @param source the source hub.
     * @param connection the connecting leg.
     * @param target the target hub.
     */
    void connectHubsByLeg(Hub source, Leg connection, Hub target);

    /**
     * Validate the supply chain.
     *
     * @return true if validation succeeds.
     */
    boolean validate();

    /**
     * Get the SupplyChain.
     *
     * @return the supply chain.
     */
    SupplyChain getSupplyChain();

}
