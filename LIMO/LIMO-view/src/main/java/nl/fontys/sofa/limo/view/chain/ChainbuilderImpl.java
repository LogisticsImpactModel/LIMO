package nl.fontys.sofa.limo.view.chain;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.hub.Hub;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class ChainbuilderImpl implements ChainBuilder {

    private List<Hub> hubList;

    public ChainbuilderImpl() {
        hubList = new ArrayList<>();
    }

    @Override
    public void addHub(Hub hub) {
        hubList.add(hub);
    }

}
