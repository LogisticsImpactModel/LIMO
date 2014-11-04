package nl.fontys.sofa.limo.view.factory;

import java.util.List;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.EventRootNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * ChildFactory for which holds the different components for building a chain.
 *
 * @author Sebastiaan Heijmann
 */
public class CategoryChildFactory extends ChildFactory<Node>{

	@Override
	protected boolean createKeys(List<Node> list) {
		try {
			Children eventChildren =
				Children.create(new EventChildFactory(), true);
			Node eventRootNode = new EventRootNode(eventChildren);
			eventRootNode.setDisplayName("Events");
			list.add(eventRootNode);

			//TODO
			// Add more components to the list
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
