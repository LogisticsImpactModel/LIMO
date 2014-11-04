package nl.fontys.sofa.limo.view.factory;

import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.view.node.EventRootNode;
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
			list = createNodeList();
		} catch (ServiceNotFoundException ex) {
			Exceptions.printStackTrace(ex);
		}
		return true;
	}

	@Override
	protected Node createNodeForKey(Node key) {
		return key;
	}

	private List<Node> createNodeList() throws ServiceNotFoundException{
		List<Node> nodeList = new ArrayList<>();
		Children hubChildren =
			Children.create(new HubChildFactory(), true);
		Node hubRootNode = new HubRootNode(hubChildren);
		hubRootNode.setDisplayName("Hubs");
		nodeList.add(hubRootNode);

		Children eventChildren =
			Children.create(new EventChildFactory(), true);
		Node eventRootNode = new EventRootNode(eventChildren);
		hubRootNode.setDisplayName("Events");
		nodeList.add(eventRootNode);
		//TODO
		// Other chain components...
		return nodeList;
	}
}
