package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.node.HubNode;
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
 * Factory for creating the CostCategoryNode children.
 *
 * @author Sebastiaan Heijmann
 */
public class HubChildFactory extends ChildFactory<Hub>
		implements LookupListener, NodeListener{

	private final Result<Hub> lookupResult;
	private HubService service; 

	public HubChildFactory() {
		service = Lookup.getDefault().lookup(HubService.class);
		lookupResult = service.getLookup().lookupResult(Hub.class);
		lookupResult.addLookupListener(this);
	}
	
	@Override
	protected boolean createKeys(List<Hub> list) {
		Collection<? extends Hub> tcl = service.findAll();
		list.addAll(tcl);
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
	public void childrenAdded(NodeMemberEvent nme) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void childrenRemoved(NodeMemberEvent nme) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void childrenReordered(NodeReorderEvent nre) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void nodeDestroyed(NodeEvent ne) {
		Node node = ne.getNode();
		Hub pc = node.getLookup().lookup(Hub.class);
		service.delete(pc);
		refresh(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent pce) {
	}
}