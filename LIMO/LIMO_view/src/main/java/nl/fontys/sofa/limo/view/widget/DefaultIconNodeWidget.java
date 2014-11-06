package nl.fontys.sofa.limo.view.widget;

import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class DefaultIconNodeWidget extends IconNodeWidget{

	public DefaultIconNodeWidget(Scene scene, ContainerNode container) {
		super(scene);
		setLabel(container.getDisplayName());
		setImage(container.getImage());
	}

}
