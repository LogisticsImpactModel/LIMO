package nl.fontys.sofa.limo.view.widget;

import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 * Widget responsible for displaying a Leg in a GraphScene. LegWidgets can be
 * added to a connection widget.
 *
 * @author Sebastiaan Heijmann
 */
public class LegWidget extends IconNodeWidget{
	private ContainerNode container;

	public LegWidget(Scene scene, ContainerNode container) {
		super(scene);
		this.container = container;
		setImage(container.getImage());
		setLabel(container.getDisplayName());
	}

	public Leg getLeg(){
		return container.getBeanNode().getLookup().lookup(Leg.class);
	}
}
