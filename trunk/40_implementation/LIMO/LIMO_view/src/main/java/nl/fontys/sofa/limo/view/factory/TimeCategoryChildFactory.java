package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.api.service.provider.CategoryService;
import nl.fontys.sofa.limo.domain.category.TimeCategory;
import nl.fontys.sofa.limo.view.node.TimeCategoryNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Factor for creating the time category children.
 *
 * @author Sebastiaan Heijmann
 */
public class TimeCategoryChildFactory extends ChildFactory<TimeCategory> 
		implements LookupListener{

	private final Result<TimeCategory> lookupResult;
	private CategoryService service; 

	public TimeCategoryChildFactory() {
		service = Lookup.getDefault().lookup(CategoryService.class);
		lookupResult = service.getLookup().lookupResult(TimeCategory.class);
		lookupResult.addLookupListener(this);
	}

	@Override
	protected boolean createKeys(List<TimeCategory> list) {
		Collection<? extends TimeCategory> tcl = service.findAllTimeCategories();
		list.addAll(tcl);
		return true;
	}

	@Override
	protected Node createNodeForKey(TimeCategory key) {
		BeanNode node = null;
		try {
			node = new TimeCategoryNode(key);
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
