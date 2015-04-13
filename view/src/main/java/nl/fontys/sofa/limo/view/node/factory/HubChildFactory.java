package nl.fontys.sofa.limo.view.node.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.nodes.NodeEvent;
import org.openide.nodes.NodeListener;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Factory responsible for creating the Hub children. It listens to changes in
 * the service layer and on the child nodes.
 *
 * @author Sebastiaan Heijmann
 */
public class HubChildFactory extends ChildFactory<Hub>
        implements LookupListener, NodeListener, Lookup.Provider {

    private final HubService service;
    private final Result<Hub> lookupResult;

    /**
     * Constructor creates a new HubChildFactory and attaches
     * {@link org.openide.util.LookupListener} on the child factories to listen
     * for changes in the data models.
     */
    public HubChildFactory() {
        service = Lookup.getDefault().lookup(HubService.class);
        lookupResult = service.getLookup().lookupResult(Hub.class);
        lookupResult.addLookupListener(this);
    }

    @Override
    protected boolean createKeys(List<Hub> list) {
        list.addAll(lookupResult.allInstances());
        return true;
    }

    @Override
    protected Node createNodeForKey(Hub key) {
        BeanNode node = null;
        try {
            node = new HubNode(key);
            node.addNodeListener(this);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

    @Override
    public void resultChanged(LookupEvent le) {
        refresh(true);
    }

    @Override
    public void nodeDestroyed(NodeEvent ne) {
        refresh(true);
    }

    @Override
    public void childrenAdded(NodeMemberEvent ev) {
        refresh(true);
    }

    @Override
    public void childrenRemoved(NodeMemberEvent ev) {
        refresh(true);
   }

    @Override
    public  void childrenReordered(NodeReorderEvent ev) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }

    @Override
    public Lookup getLookup() {
        return service.getLookup();
    }
}
