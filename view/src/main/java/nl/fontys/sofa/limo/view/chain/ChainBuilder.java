package nl.fontys.sofa.limo.view.chain;

import java.awt.event.ActionListener;
import java.util.List;
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
     * Add a hub to the supply chain.
     *
     * @param hub the hub to be added.
     */
    void addHub(Hub hub);

    /**
     * Returns the hublist.
     */
    List<Hub> getHubList();

    void addListener(ActionListener listener);

    void removeListener(ActionListener listener);
    
    void modify();

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
     * Get the start hub of the supply chain.
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
     * Remove the leg from the chain.
     *
     * @param leg the leg to be removed.
     */
    void disconnectLeg(Leg leg);

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
