package nl.fontys.sofa.limo.view.factory;

import java.util.List;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.node.AbstractRootNode;
import nl.fontys.sofa.limo.view.node.HubRootNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * ChildFactory which creates the different components for building a chain to
 * display them in the palette.
 * <p>
 * At the moment only Hubs are added to this factory which are then displayed in
 * the palette. To add more elements to the palette simply instantiate a new
 * ChildFactory in the createKeys method and add it to the list of
 * AbstractRootNodes.
 *
 * @author Sebastiaan Heijmann
 */
public class CategoryChildFactory extends ChildFactory<AbstractRootNode>
        implements LookupListener {

    private HubChildFactory hubFactory;
    private Result<Hub> lookupResult;

    public CategoryChildFactory() {
        hubFactory = new HubChildFactory();
        lookupResult = hubFactory.getLookup().lookupResult(Hub.class);
        lookupResult.addLookupListener(this);
    }

    @Override
    protected boolean createKeys(List<AbstractRootNode> list) {
        try {
            Children hubChildren
                    = Children.create(new HubChildFactory(), true);
            AbstractRootNode hubRootNode = new HubRootNode(hubChildren);
            hubRootNode.setDisplayName("Hubs");
            list.add(hubRootNode);
        } catch (ServiceNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        return true;
    }

    @Override
    protected AbstractRootNode createNodeForKey(AbstractRootNode key) {
        return key;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        refresh(true);
    }

}
