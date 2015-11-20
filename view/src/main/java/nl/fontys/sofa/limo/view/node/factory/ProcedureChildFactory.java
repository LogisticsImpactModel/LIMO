package nl.fontys.sofa.limo.view.node.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.node.bean.ProcedureNode;
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
 * Factory responsible for creating the ProcedureCategory children.
 * <p>
 * It listens to changes in the service layer by implementing
 * {@link  org.openide.util.LookupListener} and to changes in the nodes itself
 * by implementing {@link org.openide.nodes.NodeListener}.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureChildFactory extends ChildFactory<Procedure>
        implements LookupListener, NodeListener {

    private final Result<Procedure> lookupResult;
    private final ProcedureService service;

    /**
     * Constructor creates a new ProcedureCategoryChildFactory and attaches
     * {@link org.openide.util.LookupListener} on the child factories to listen
     * for changes in the data models.
     */
    public ProcedureChildFactory() {
        service = Lookup.getDefault().lookup(ProcedureService.class);
        lookupResult = service.getLookup().lookupResult(Procedure.class);
        lookupResult.addLookupListener(this);
    }

    @Override
    protected boolean createKeys(List<Procedure> list) {
        list.addAll(lookupResult.allInstances());
        return true;
    }

    @Override
    protected Node createNodeForKey(Procedure key) {
        BeanNode node = null;
        try {
            node = new ProcedureNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        if (node != null) {
            node.addNodeListener(this);
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
    public void childrenAdded(NodeMemberEvent nme) {
    }

    @Override
    public void childrenRemoved(NodeMemberEvent nme) {
    }

    @Override
    public void childrenReordered(NodeReorderEvent nre) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
    }
}
