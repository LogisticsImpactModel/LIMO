package nl.fontys.sofa.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.api.dao.DAOFactory;
import nl.fontys.sofa.limo.api.dao.HubTypeDAO;
import nl.fontys.sofa.limo.domain.types.HubType;
import nl.fontys.sofa.limo.view.node.HubTypeNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 * Factor for creating the HubType children.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeChildFactory extends ChildFactory<HubType>{

	@Override
	protected boolean createKeys(List<HubType> list) {
		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
		HubTypeDAO htd = df.getHubTypeDAO();
		List<HubType> htl = htd.findAll();
		for(HubType ht : htl){
			list.add(ht);
		}
		return true;
	}

	@Override
	protected Node createNodeForKey(HubType key) {
		BeanNode node = null;
		try {
			node = new HubTypeNode(key);
		} catch (IntrospectionException ex) {
			Exceptions.printStackTrace(ex);
		}
		return node;
	}

}
