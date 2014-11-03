package nl.fontys.sofa.limo.view.factory;

import java.util.List;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.HubRootNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class CategoryChildFactory extends ChildFactory<Node>{

	@Override
	protected boolean createKeys(List<Node> list) {
		try {
			Children children =
				Children.create(new EventChildFactory(), true);
			Node hubRootNode = new HubRootNode(children);
			list.add(hubRootNode);
		} catch (ServiceNotFoundException ex) {
			Exceptions.printStackTrace(ex);
		}
		return true;
	}

	@Override
	protected Node createNodeForKey(Node key) {
		return key;
	}
}
