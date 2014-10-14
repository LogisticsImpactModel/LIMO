package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.process.ProcedureCategory;
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
 * Factory for creating the cost category children.
 *
 * @author Sebastiaan Heijmann
 */
public class ProcedureCategoryChildFactory extends ChildFactory<ProcedureCategory>
		implements LookupListener{

	private final Result<ProcedureCategory> lookupResult;
	private ProcedureService service; 

	public ProcedureCategoryChildFactory() {
		service = Lookup.getDefault().lookup(ProcedureService.class);
		lookupResult = service.getLookup().lookupResult(ProcedureCategory.class);
		lookupResult.addLookupListener(this);
	}
	
	@Override
	protected boolean createKeys(List<ProcedureCategory> list) {
		Collection<? extends ProcedureCategory> tcl = service.findAllProcessCategories();
		list.addAll(tcl);
		return true;
	}

    @Override
    protected Node createNodeForKey(ProcedureCategory key) {
        BeanNode node = null;
        try {
			node = new ProcedureCategoryNode(key);
        } catch (IntrospectionException ex) {
            Exceptions.printStackTrace(ex);
        }
        return node;
    }

	@Override
	public void resultChanged(LookupEvent le) {
	    refresh(true);
	}
}
