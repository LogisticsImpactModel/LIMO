package nl.fontys.sofa.limo.view.widget;

import java.beans.IntrospectionException;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.node.LegNode;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 * Widget responsible for displaying a connection between Hubs in a GraphScene.
 * LegWidgets can be added to this connection widget.
 *
 * @author Sebastiaan Heijmann
 */
public class LegConnectionWidget extends ConnectionWidget {

	public LegConnectionWidget(Scene scene, LegType legType) throws IntrospectionException {
		super(scene);
		setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
		setEndPointShape(PointShape.SQUARE_FILLED_BIG);

		Leg leg = new Leg();
		leg.setIcon(legType.getIcon());
		leg.setName(legType.getName());
		LegNode node = new LegNode(leg);
		ContainerNode container = new ContainerNode(node);
		LegWidget legWidget = new LegWidget(scene, container);
		setConstraint(legWidget, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 20);
		legWidget.setOpaque(true);
		addChild(legWidget);
	}

	public LegConnectionWidget(Scene scene, List<LegType> legTypes) throws IntrospectionException {
		super(scene);
		setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
		setEndPointShape(PointShape.SQUARE_FILLED_BIG);

		MultiModeLeg multiModeLeg = new MultiModeLeg();
		multiModeLeg.setName("MultiMode Leg");
		for (LegType lt : legTypes) {
			Leg subLeg = new Leg();
			subLeg.setIcon(lt.getIcon());
			subLeg.setName(lt.getName());
			multiModeLeg.addLeg(subLeg);

			LegNode node = new LegNode(subLeg);
			ContainerNode container = new ContainerNode(node);
			LegWidget legWidget = new LegWidget(scene, container);
			setConstraint(legWidget, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 20);
			legWidget.setOpaque(true);
			addChild(legWidget);
		}
	}

}
