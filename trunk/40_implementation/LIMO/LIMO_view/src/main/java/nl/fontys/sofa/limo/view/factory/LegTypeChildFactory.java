package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.types.LegType;
import nl.fontys.sofa.limo.view.node.LegTypeNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Factory for creating the leg type children.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeChildFactory extends ChildFactory<LegType> 
		implements LookupListener{

	private final Result<BaseEntity> lookupResult;
	private LegTypeDAO ltd; 

	public LegTypeChildFactory() {
		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
		ltd = df.getLegTypeDAO();

		lookupResult = null;
//		lookupResult = ltd.getLookup().lookupResult(BaseEntity.class);
//		lookupResult.addLookupListener(this);
	}

	@Override
	protected boolean createKeys(List<LegType> list) {
		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
		LegTypeDAO ltd = df.getLegTypeDAO();
		List<LegType> htl = ltd.findAll();
		for(LegType lt : htl){
			list.add(lt);
		}
		return true;
	}

	@Override
	protected Node createNodeForKey(LegType key) {
		BeanNode node = null;
		try {
			node = new LegTypeNode(key);
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
