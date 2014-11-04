package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.ProcedureCategoryService;
import nl.fontys.sofa.limo.domain.component.procedure.ProcedureCategory;
import nl.fontys.sofa.limo.view.node.ProcedureCategoryNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Factory responsible for creating the CostCategoryNode children.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryChildFactory extends ChildFactory<ProcedureCategory>
		implements LookupListener/*, NodeListener*/{

	private final Result<ProcedureCategory> lookupResult;
	private final ProcedureCategoryService service; 

	public ProcedureCategoryChildFactory() {
		service = Lookup.getDefault().lookup(ProcedureCategoryService.class);
		lookupResult = service.getLookup().lookupResult(ProcedureCategory.class);
		lookupResult.addLookupListener(this);
	}
	
	@Override
	protected boolean createKeys(List<ProcedureCategory> list) {
		Collection<? extends ProcedureCategory> tcl = lookupResult.allInstances();
		list.addAll(tcl);
		return true;
	}

    @Override
    protected Node createNodeForKey(ProcedureCategory key) {
        BeanNode node = null;
        try {
			node = new ProcedureCategoryNode(key);
//			node.addNodeListener(this);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
		}
        return node;
    }

	@Override
	public void resultChanged(LookupEvent le) {
	    refresh(false);
	}

//	@Override
//	public void childrenAdded(NodeMemberEvent nme) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//	}
//
//	@Override
//	public void childrenRemoved(NodeMemberEvent nme) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//	}
//
//	@Override
//	public void childrenReordered(NodeReorderEvent nre) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//	}
//
//	@Override
//	public void nodeDestroyed(NodeEvent ne) {
//		Node node = ne.getNode();
//		Collection<? extends ProcedureCategoryNode> selectedNode = Utilities.actionsGlobalContext().lookupAll(ProcedureCategoryNode.class);
//		for(Node n : selectedNode){
//			if(n == node){
//				ProcedureCategory pc = node.getLookup().lookup(ProcedureCategory.class);
//				service.delete(pc);
//			}
//		}
//		refresh(true);
//	}
//
//	@Override
//	public void propertyChange(PropertyChangeEvent pce) {
//	}
}
