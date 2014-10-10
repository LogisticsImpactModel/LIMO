package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.TimeCategoryDAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
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

	private final Result<BaseEntity> lookupResult;
	private TimeCategoryDAO tcd; 

	public TimeCategoryChildFactory() {
		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
		tcd = df.getTimeCategoryDAO();

		lookupResult = tcd.getLookup().lookupResult(BaseEntity.class);
		lookupResult.addLookupListener(this);
	}

	@Override
	protected boolean createKeys(List<TimeCategory> list) {
		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
		TimeCategoryDAO tcd = df.getTimeCategoryDAO();
		List<TimeCategory> tcl = tcd.findAll();
		for(TimeCategory tc : tcl){
			list.add(tc);
		}
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
