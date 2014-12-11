package nl.fontys.sofa.limo.view.node.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.node.HubTypeNode;
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
import org.openide.util.Utilities;

/**
 * Factory responsible for creating the HubType children. It listens to changes
 * in the service layer and in the nodes.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeChildFactory extends ChildFactory<HubType>
        implements LookupListener, NodeListener {

    private final Result<HubType> lookupResult;
    private final HubTypeService service;

    public HubTypeChildFactory() {
        service = Lookup.getDefault().lookup(HubTypeService.class);
        lookupResult = service.getLookup().lookupResult(HubType.class);
        lookupResult.addLookupListener(this);
    }

    @Override
    protected boolean createKeys(List<HubType> list) {
        list.addAll(lookupResult.allInstances());
        return true;
    }

    @Override
    protected Node createNodeForKey(HubType key) {
        BeanNode node = null;
        try {
            node = new HubTypeNode(key);
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
    }

    @Override
    public void childrenRemoved(NodeMemberEvent ev) {
    }

    @Override
    public void childrenReordered(NodeReorderEvent ev) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }

}
