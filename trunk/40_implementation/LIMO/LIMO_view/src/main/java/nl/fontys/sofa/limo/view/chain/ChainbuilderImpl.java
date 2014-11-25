package nl.fontys.sofa.limo.view.chain;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * Implementation of the ChainBuilder interface. Enables building and validating
 * a supply chain.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainbuilderImpl implements ChainBuilder {

    private final SupplyChain chain;
    private final List<Hub> hubList;

    public ChainbuilderImpl() {
        chain = new SupplyChain();
        hubList = new ArrayList<>();
    }

    @Override
    public void addHub(Hub hub) {
        hubList.add(hub);
    }

    @Override
    public int getNumberOfHubs() {
        return hubList.size();
    }

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

    @Override
    public boolean validate() {
        int hubCount = 1;
        Node currentNode = getStartHub();
        while (currentNode != null) {
            currentNode = currentNode.getNext();
            if (currentNode instanceof Hub) {
                hubCount++;
            }
        }
        return hubCount == getNumberOfHubs();
    }

}
