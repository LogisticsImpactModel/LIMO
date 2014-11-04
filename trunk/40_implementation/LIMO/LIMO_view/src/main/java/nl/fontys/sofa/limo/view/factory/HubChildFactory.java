package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
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
import org.openide.util.Utilities;

/**
 * Factory responsible for creating the Hub children. It listens
 * to changes in the service layer and in the nodes.
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
	public void nodeDestroyed(NodeEvent ne) {
		Node node = ne.getNode();
		Hub hub =
				(Hub) node.getLookup().lookup(Hub.class);

		Lookup.Result result = Utilities.actionsGlobalContext().lookupResult (Hub.class);
		Collection<Hub> selectedBeans = result.allInstances();
		for(Hub bean : selectedBeans){
			if(bean == hub){
				service.delete(hub);
			}	
		}
		refresh(true);
	}

	@Override
	public void childrenAdded(NodeMemberEvent ev) {}
	@Override
	public void childrenRemoved(NodeMemberEvent ev) {}
	@Override
	public void childrenReordered(NodeReorderEvent ev) {}
	@Override
	public void propertyChange(PropertyChangeEvent pce) {}

}