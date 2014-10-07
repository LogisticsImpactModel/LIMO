package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.LegTypeDAO;
import nl.fontys.sofa.limo.domain.types.LegType;
import nl.fontys.sofa.limo.view.node.LegTypeNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * Factory for creating the leg type children.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeChildFactory extends ChildFactory<LegType>{

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
}
