package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.node.ProcedureCategoryNode;
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
 * Factory responsible for creating the ProcedureCategory children. It listens
 * to changes in the service layer and in the nodes.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryChildFactory extends ChildFactory<ProcedureCategory>
		implements LookupListener, NodeListener {

	private final Result<ProcedureCategory> lookupResult;
	private final ProcedureCategoryService service;

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
		Node node = ne.getNode();
		ProcedureCategory pc =
				(ProcedureCategory) node.getLookup().lookup(ProcedureCategory.class);

		Lookup.Result result = Utilities.actionsGlobalContext().lookupResult (ProcedureCategory.class);
		Collection<ProcedureCategory> selectedBeans = result.allInstances();
		for(ProcedureCategory bean : selectedBeans){
			if(bean == pc){
				service.delete(pc);
			}	
		}
		refresh(true);
	}

	@Override
	public void childrenAdded(NodeMemberEvent nme) {}
	@Override
	public void childrenRemoved(NodeMemberEvent nme) {}
	@Override
	public void childrenReordered(NodeReorderEvent nre) {}
	@Override
	public void propertyChange(PropertyChangeEvent pce) {}
}
