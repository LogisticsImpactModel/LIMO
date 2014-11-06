package nl.fontys.sofa.limo.view.widget;

import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 * Widget which holds a ContainerNode containing a HubNode. This Widget can be
 * used to display a hub in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public class HubWidget extends DefaultIconNodeWidget{

	/**
	 * Constructor sets up the widget by setting the display name and image.
	 * @param scene - the scene to display the Widget on.
	 * @param container - the container for the HubNode.
	 */
	public HubWidget(Scene scene, ContainerNode container) {
		super(scene, container);
	}


}
