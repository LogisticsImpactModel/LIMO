package nl.fontys.sofa.limo.view.custom.pane;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.geom.AffineTransform;
import java.beans.BeanInfo;
import java.util.Collections;
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
import org.netbeans.api.visual.action.HoverProvider;
import org.netbeans.api.visual.action.ReconnectProvider;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.model.ObjectScene;
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

    private LayerWidget backgroundLayer = new LayerWidget(this);
    private LayerWidget mainLayer = new LayerWidget(this);
    private LayerWidget connectionLayer = new LayerWidget(this);
    private LayerWidget interactionLayer = new LayerWidget(this);
//    private LayerWidget interactionLayer = new LayerWidget(this);
	private WidgetAction connectAction
			= ActionFactory.createExtendedConnectAction(
					interactionLayer,
					new SceneConnectProvider());
	private final WidgetAction reconnectAction
			= ActionFactory.createReconnectAction(
					new SceneReconnectProvider());
	private final WidgetAction moveAlignAction;
	private final WidgetAction moveAction = ActionFactory.createMoveAction();

	/**
	 * Constructor creates two layers and defines an acceptaction.
	 */
	public ChainScene() {
        addChild(backgroundLayer);
        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interactionLayer);

		moveAlignAction = ActionFactory.createAlignWithMoveAction(mainLayer, interactionLayer, null);
		getActions().addAction(ActionFactory.createZoomAction());
		getActions().addAction(ActionFactory.createPanAction());
		getActions().addAction(ActionFactory.createAcceptAction(new SceneAcceptProvider()));
	}

	@Override
	protected Widget attachNodeWidget(ContainerNode n) {
//		Widget widget = WidgetFactory.createWidgetFromContainerNode(this, n);
//		widget.getActions().addAction(createSelectAction());
//        widget.getActions().addAction(createObjectHoverAction());
//		widget.getActions().addAction(connectAction);
//		widget.getActions().addAction(moveAlignAction);
//		mainLayer.addChild(widget);
//		return widget;
		return null;
	}

	@Override
	protected Widget attachEdgeWidget(String e) {
		ConnectionWidget connection = new ConnectionWidget(this);
		connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        connection.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
//		connection.setRouter(RouterFactory.createOrthogonalSearchRouter(
//				mainLayer,
//				connectionLayer));
        connection.getActions().addAction(createObjectHoverAction());
		connection.getActions().addAction(createSelectAction());
		connection.getActions().addAction(reconnectAction);
		connectionLayer.addChild(connection);
		return null;
	}

	@Override
	protected void attachEdgeSourceAnchor(String e, ContainerNode n, ContainerNode n1) {
		ConnectionWidget connection = (ConnectionWidget) findWidget(e);
		Widget widget = findWidget(n1);
		Anchor anchor = AnchorFactory.createRectangularAnchor(widget);
		connection.setSourceAnchor(anchor);
	}

	@Override
	protected void attachEdgeTargetAnchor(String e, ContainerNode n, ContainerNode n1) {
		ConnectionWidget connection = (ConnectionWidget) findWidget(e);
		Widget widget = findWidget(n1);
		Anchor anchor = AnchorFactory.createRectangularAnchor(widget);
		connection.setTargetAnchor(anchor);
	}

	/**
	 * Accept provider for the scene. Validates if a connection can be dropped
	 * and places the connection in the scene.
	 */
	private class SceneAcceptProvider implements AcceptProvider {

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

	/**
	 * Select provider for the scene. Enables selection of objects.
	 */
	private class ObjectSelectProvider implements SelectProvider {

		@Override
		public boolean isAimingAllowed(
				Widget widget,
				Point localLocation,
				boolean invertSelection) {

			return false;
		}

		@Override
		public boolean isSelectionAllowed(
				Widget widget,
				Point localLocation,
				boolean invertSelection) {

			return findObject(widget) != null;
		}

		@Override
		public void select(
				Widget widget,
				Point localLocation,
				boolean invertSelection) {

			Object object = findObject(widget);

			setFocusedObject(object);
			if (object != null) {
				if (!invertSelection && getSelectedObjects().contains(object)) {
					return;
				}
				userSelectionSuggested(
						Collections.singleton(object),
						invertSelection);
			} else {
				userSelectionSuggested(
						Collections.emptySet(),
						invertSelection);
			}
		}
	}

	/**
	 * Connect provider for the scene. Validates and connects widgets to each
	 * other.
	 */
	private class SceneConnectProvider implements ConnectProvider {

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

	/**
	 * ReconnectProvider for the scene. Enable reconnecting widgets to
	 * eachother.
	 */
	private class SceneReconnectProvider implements ReconnectProvider {

		String edge;
		ContainerNode originalNode;
		ContainerNode replacementNode;

		@Override
		public void reconnectingStarted(
				ConnectionWidget connectionWidget,
				boolean reconnectingSource) {
		}

		@Override
		public void reconnectingFinished(
				ConnectionWidget connectionWidget,
				boolean reconnectingSource) {
		}

		@Override
		public boolean isSourceReconnectable(ConnectionWidget connectionWidget) {
			Object object = findObject(connectionWidget);
			edge = isEdge(object) ? (String) object : null;
			originalNode = edge != null ? getEdgeSource(edge) : null;
			return originalNode != null;
		}

		@Override
		public boolean isTargetReconnectable(ConnectionWidget connectionWidget) {
			Object object = findObject(connectionWidget);
			edge = isEdge(object) ? (String) object : null;
			originalNode = edge != null ? getEdgeTarget(edge) : null;
			return originalNode != null;
		}

		@Override
		public boolean hasCustomReplacementWidgetResolver(Scene scene) {
			return false;
		}

		@Override
		public Widget resolveReplacementWidget(Scene scene, Point sceneLocation) {
			return null;
		}

		@Override
		public void reconnect(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
			if (replacementWidget == null) {
				removeEdge(edge);
			} else if (reconnectingSource) {
				setEdgeSource(edge, replacementNode);
			} else {
				setEdgeTarget(edge, replacementNode);
			}
		}

		@Override
		public ConnectorState isReplacementWidget(ConnectionWidget connectionWidget, Widget replacementWidget, boolean b) {
			Object object = findObject(replacementWidget);
			replacementNode = isNode(object) ? (ContainerNode) object : null;
			if (replacementNode != null) {
				return ConnectorState.ACCEPT;
			}
			return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
		}

	}

	/**
	 * Get the main layer of the scene.
	 *
	 * @return LayerWidget - the main layer as a LayerWdiget.
	 */
	public LayerWidget getMainLayer() {
		return mainLayer;
	}

	/**
	 * Get the connection layer of the scene.
	 *
	 * @return LayerWidget - the connection layer as a LayerWidget.
	 */
	public LayerWidget getConnectionLayer() {
		return connectionLayer;
	}

	public void removeNodeWithAttachedEdges(ContainerNode node) {
		for (String edge : ChainScene.this.findNodeEdges(node, true, true)) {
			ChainScene.this.removeEdge(edge);
		}
		ChainScene.this.removeNode(node);
	}
}
