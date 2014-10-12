package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.service.provider.CategoryService;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.category.CostCategory;
import nl.fontys.sofa.limo.view.node.CostCategoryNode;
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
public class CostCategoryChildFactory extends ChildFactory<CostCategory>
		implements LookupListener{

	private final Result<CostCategory> lookupResult;
	private CategoryService service; 

	public CostCategoryChildFactory() {
		service = Lookup.getDefault().lookup(CategoryService.class);
		lookupResult = service.getLookup().lookupResult(CostCategory.class);
		lookupResult.addLookupListener(this);
	}
	
	@Override
	protected boolean createKeys(List<CostCategory> list) {
		Collection<? extends CostCategory> tcl = service.findAllCostCategories();
		list.addAll(tcl);
		return true;
	}

    @Override
    protected Node createNodeForKey(CostCategory key) {
        BeanNode node = null;
        try {
			node = new CostCategoryNode(key);
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
