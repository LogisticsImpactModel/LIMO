package nl.fontys.sofa.limo.view.node.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.node.bean.ProcedureCategoryNode;
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
public class ProcedureCategoryChildFactory extends ChildFactory<ProcedureCategory>
        implements LookupListener, NodeListener {

    private final Result<ProcedureCategory> lookupResult;
    private final ProcedureCategoryService service;

    /**
     * Constructor creates a new ProcedureCategoryChildFactory and attaches
     * {@link org.openide.util.LookupListener} on the child factories to listen
     * for changes in the data models.
     */
    public ProcedureCategoryChildFactory() {
        service = Lookup.getDefault().lookup(ProcedureCategoryService.class);
        lookupResult = service.getLookup().lookupResult(ProcedureCategory.class);
        lookupResult.addLookupListener(this);
    }

    @Override
    protected boolean createKeys(List<ProcedureCategory> list) {
        list.addAll(lookupResult.allInstances());
        return true;
    }

    @Override
    protected Node createNodeForKey(ProcedureCategory key) {
        BeanNode node = null;
        try {
            node = new ProcedureCategoryNode(key);
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
