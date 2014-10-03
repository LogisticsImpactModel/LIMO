package nl.fontys.limo.view.factory;

import java.beans.IntrospectionException;
import java.util.List;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * Factor for creating the Time Category children.
 *
 * @author Sebastiaan Heijmann
 */
public class TimeCategoryChildFactory extends ChildFactory<String>{

	@Override
	protected boolean createKeys(List<String> list) {
//		DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
//		TimeCategoryDAO tcd = df.getTimeCategoryDAO();
//		List<TimeCategory> tcl = tcd.findAll();
//		for(TimeCategory tc : tcl){
//			list.add(tc.getIdentifier());
//		}
		list.add("Test");
		list.add("Test2");
		return true;
	}

	@Override
	protected Node createNodeForKey(String key) {
		BeanNode node = null;
		try {
			node = new BeanNode(key);
			node.setDisplayName(key);
		} catch (IntrospectionException ex) {
			Exceptions.printStackTrace(ex);
		}
		return node;
	}
}
