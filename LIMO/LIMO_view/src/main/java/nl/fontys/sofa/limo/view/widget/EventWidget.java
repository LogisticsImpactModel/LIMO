package nl.fontys.sofa.limo.view.widget;

import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 * Widget which holds a ContainerNode containing an EventNode. This Widget can
 * be used to display an Event in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public class EventWidget extends IconNodeWidget{

	/**
	 * Constructor sets up the widget by setting the display name and image.
	 * @param scene - the scene to display the Widget on.
	 * @param container - the container for the EventNode.
	 */
	public EventWidget(Scene scene, ContainerNode container) {
		super(scene);
		setLabel(container.getDisplayName());
		setImage(container.getImage());
	}

}
