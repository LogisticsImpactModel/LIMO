package nl.fontys.sofa.limo.view.node.factory;

import java.util.List;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.node.root.AbstractRootNode;
import nl.fontys.sofa.limo.view.node.root.HubRootNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * ChildFactory which creates the different components which are visible in the
 * palette.
 * <p>
 * This factory uses the other {@link org.openide.nodes.ChildFactory} classes to
 * create the separate children. At the moment only a factory for hubs is used
 * and added as a category.
 * <p>
 * To add more categories to the palette simply instantiate a new ChildFactory
 * in the createKeys method and add it to the list of AbstractRootNodes. Than
 * the new category will be displayed in the palette.
 *
 * @author Sebastiaan Heijmann
 */
public class CategoryChildFactory extends ChildFactory<AbstractRootNode>
        implements LookupListener {

    private HubChildFactory hubFactory;
    private Result<Hub> lookupResult;

    /**
     * Constructor creates a new CategoryChildFactory and attaches
     * {@link org.openide.util.LookupListener} on the child factories to listen
     * for changes in the data models.
     */
    public CategoryChildFactory() {
        hubFactory = new HubChildFactory();
        lookupResult = hubFactory.getLookup().lookupResult(Hub.class);
        lookupResult.addLookupListener(this);
    }

    @Override
    protected boolean createKeys(List<AbstractRootNode> list) {
        try {
            hubFactory = new HubChildFactory();

            Children hubChildren = Children.create(hubFactory, false);

            AbstractRootNode hubRootNode = new HubRootNode(hubChildren);
            hubRootNode.setDisplayName(LIMOResourceBundle.getString("HUBS"));
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
