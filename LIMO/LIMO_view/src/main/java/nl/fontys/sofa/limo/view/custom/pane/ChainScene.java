package nl.fontys.sofa.limo.view.custom.pane;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.geom.AffineTransform;
import java.beans.BeanInfo;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.view.factory.WidgetFactory;
import nl.fontys.sofa.limo.view.node.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.node.HubNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;

/**
 * The GraphScene where the chain is displayed on. It provides implementations
 * for the abstract attach methods from GraphScene and sets up two LayerWidgets.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainScene extends GraphScene<ContainerNode, String> {

	private final LayerWidget mainLayer;
	private final LayerWidget connectionLayer;

	public ChainScene() {
		mainLayer = new LayerWidget(this);
		addChild(mainLayer);
		connectionLayer = new LayerWidget(this);
		addChild(connectionLayer);

		getActions().addAction(ActionFactory.createAcceptAction(new AcceptAction()));
//		mainLayer.setLayout(LayoutFactory.createHorizontalFlowLayout());
//		GridGraphLayout layout = new GridGraphLayout();
//		SceneLayout sceneLayout = LayoutFactory.createSceneGraphLayout(this, layout);
//		sceneLayout.invokeLayout();
	}

	@Override
	protected Widget attachNodeWidget(ContainerNode n) {
		Widget widget = WidgetFactory.createWidgetFromContainerNode(this, n);
		widget.getActions().addAction(ActionFactory.createExtendedConnectAction(
				connectionLayer,
				new ConnectAction()));
		widget.getActions().addAction(ActionFactory.createAlignWithMoveAction(
				mainLayer,
				connectionLayer,
				ActionFactory.createDefaultAlignWithMoveDecorator()));

		mainLayer.addChild(widget);
		return widget;
	}

	@Override
	protected Widget attachEdgeWidget(String e) {
		ConnectionWidget widget = new ConnectionWidget(this);
		widget.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
		widget.setRouter(RouterFactory.createOrthogonalSearchRouter(mainLayer, connectionLayer));
		connectionLayer.addChild(widget);
		return null;
	}

	@Override
	protected void attachEdgeSourceAnchor(String e, ContainerNode n, ContainerNode n1) {
		ConnectionWidget connection = (ConnectionWidget) findWidget(e);
		Widget widget = findWidget(n1);
		Anchor anchor = AnchorFactory.createRectangularAnchor(widget);
		connection.setTargetAnchor(anchor);
	}

	@Override
	protected void attachEdgeTargetAnchor(String e, ContainerNode n, ContainerNode n1) {
		ConnectionWidget connection = (ConnectionWidget) findWidget(e);
		Widget widget = findWidget(n1);
		Anchor anchor = AnchorFactory.createRectangularAnchor(widget);
		connection.setTargetAnchor(anchor);
	}
        
        private static class InitialHubAction extends WidgetAction.Adapter {

	private class AcceptAction implements AcceptProvider {

		@Override
		public ConnectorState isAcceptable(Widget widget, Point point, Transferable t) {
			Node node = NodeTransfer.node(t, NodeTransfer.DND_COPY_OR_MOVE);
			Image dragImage = node.getIcon(BeanInfo.ICON_COLOR_32x32);
			JComponent view = getView();
			Graphics2D g2 = (Graphics2D) view.getGraphics();
			Rectangle visRect = view.getVisibleRect();
			view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
			if (node instanceof HubNode) {
				g2.drawImage(dragImage,
						AffineTransform.getTranslateInstance(point.getLocation().getX(),
								point.getLocation().getY()),
						null);
				return ConnectorState.ACCEPT;
			}
			return ConnectorState.REJECT;
		}

		@Override
		public void accept(Widget widget, Point point, Transferable t) {
			AbstractBeanNode node = (AbstractBeanNode) NodeTransfer.node(
					t, NodeTransfer.DND_COPY_OR_MOVE);
			Widget w = ChainScene.this.addNode(new ContainerNode(node));
			w.setPreferredLocation(widget.convertLocalToScene(point));

		}
	}

	private class ConnectAction implements ConnectProvider {

		@Override
		public boolean isSourceWidget(Widget widget) {
			return widget != null && widget instanceof HubWidget;
		}

		@Override
		public ConnectorState isTargetWidget(Widget widget, Widget widget1) {
			return widget != widget1 && widget1 instanceof HubWidget
					? ConnectorState.ACCEPT : ConnectorState.REJECT;
		}

		@Override
		public boolean hasCustomTargetWidgetResolver(Scene scene) {
			return false;
		}

		@Override
		public Widget resolveTargetWidget(Scene scene, Point point) {
			return null;
		}

		@Override
		public void createConnection(Widget widget, Widget widget1) {
			ConnectionWidget connection = new ConnectionWidget(ChainScene.this);
			connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
			connection.setSourceAnchor(AnchorFactory.createRectangularAnchor(widget));
			connection.setTargetAnchor(AnchorFactory.createRectangularAnchor(widget1));
			connectionLayer.addChild(connection);
		}

	}
}
