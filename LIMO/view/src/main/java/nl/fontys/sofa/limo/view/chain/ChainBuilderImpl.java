package nl.fontys.sofa.limo.view.chain;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * Implementation of the {@link nl.fontys.sofa.limo.view.chain.ChainBuilder}
 * interface.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainBuilderImpl implements ChainBuilder {

    private final SupplyChain chain;
    private final List<Hub> hubList;

    /**
     * Constructor for the ChainBuilderImpl. It creates a new
     * {@link nl.fontys.sofa.limo.domain.component.SupplyChain} and a list of
     * hubs.
     */
    public ChainBuilderImpl() {
        chain = new SupplyChain();
        hubList = new ArrayList<>();
    }

    /**
     * Constructor for the ChainBuilderImpl. It opens a new
     * {@link nl.fontys.sofa.limo.domain.component.SupplyChain} and a list of
     * hubs.
     *
     * @param chain
     */
    public ChainBuilderImpl(SupplyChain chain) {
        this.chain = chain;
        hubList = new ArrayList<>();
        int hubCount = 1;
        Node currentNode = chain.getStart();
        while (currentNode != null) {
            if (currentNode instanceof Hub) {
                hubList.add((Hub) currentNode);
            }
            currentNode = currentNode.getNext();
        }
    }

    @Override
    public void addHub(Hub hub) {
        hubList.add(hub);
    }

    @Override
    public void removeHub(Hub hub) {
        hubList.remove(hub);
    }

    @Override
    public int getNumberOfHubs() {
        return hubList.size();
    }

    @Override
    public Hub getStartHub() {
        return chain.getStart();
    }

    @Override
    public void setStartHub(Hub hub) {
        chain.setStart(hub);
    }

    @Override
    public void connectHubsByLeg(Hub source, Leg connection, Hub target) {
        source.setNext(connection);
        connection.setPrevious(source);
        connection.setNext(target);
        target.setPrevious(connection);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Checks whether there are at least 2 hubs and if every hub is connected by
     * a leg.
     */
    @Override
    public boolean validate() {
        int hubCount = 1;
        Node currentNode = getStartHub();
        if (currentNode == null || getNumberOfHubs() == 1) {
            return false;
        }
        while (currentNode != null) {
            currentNode = currentNode.getNext();
            if (currentNode instanceof Hub) {
                hubCount++;
            }
        }
        return hubCount == getNumberOfHubs();
    }

    @Override
    public SupplyChain getSupplyChain() {
        return chain;
    }

}
