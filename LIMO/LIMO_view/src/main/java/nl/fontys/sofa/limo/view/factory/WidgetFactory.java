package nl.fontys.sofa.limo.view.factory;

import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.node.EventNode;
import nl.fontys.sofa.limo.view.node.HubNode;
import nl.fontys.sofa.limo.view.node.LegTypeNode;
import nl.fontys.sofa.limo.view.widget.EventWidget;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;

/**
 * Factory which creates widgets.<p> Widgets can be created from a
 * ContainerNode.
 *
 * @author Sebastiaan Heijmann
 */
public class WidgetFactory {

	/**
	 * Create a Widget from a ContainerNode
	 * @param scene - the Scene to display the Widget on.
	 * @param container
	 * @return 
	 */
	public static Widget createWidgetFromContainerNode(
			Scene scene,
			ContainerNode container) {

		Node node = container.getBeanNode();
		
		if (node instanceof HubNode) {
			HubWidget widget = new HubWidget(scene, container);
			return widget;
		} else if (node instanceof EventNode) {
			return new EventWidget(scene, container);
		} 
		return null;
	}

	private static Widget createHubWidget(){
		return new HubWidget(null, null);
	}
}
