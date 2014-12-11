package nl.fontys.sofa.limo.view.node.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.node.LegTypeNode;
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
 * Factory responsible for creating the LegType children. It listens to changes
 * in the service layer and in the nodes.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeChildFactory extends ChildFactory<LegType>
        implements LookupListener, NodeListener {

    private final Result<LegType> lookupResult;
    private final LegTypeService service;

    public LegTypeChildFactory() {
        service = Lookup.getDefault().lookup(LegTypeService.class);
        lookupResult = service.getLookup().lookupResult(LegType.class);
        lookupResult.addLookupListener(this);
    }

    @Override
    protected boolean createKeys(List<LegType> list) {
        list.addAll(lookupResult.allInstances());
        return true;
    }

    @Override
    protected Node createNodeForKey(LegType key) {
        BeanNode node = null;
        try {
            node = new LegTypeNode(key);
            node.addNodeListener(this);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        refresh(true);
    }

    @Override
    public void nodeDestroyed(NodeEvent ne) {
        Node node = ne.getNode();
        LegType lt
                = (LegType) node.getLookup().lookup(LegType.class);

        Lookup.Result result = Utilities.actionsGlobalContext().lookupResult(LegType.class);
        Collection<LegType> selectedBeans = result.allInstances();
        for (LegType bean : selectedBeans) {
            if (bean == lt) {
                service.delete(lt);
            }
        }
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
