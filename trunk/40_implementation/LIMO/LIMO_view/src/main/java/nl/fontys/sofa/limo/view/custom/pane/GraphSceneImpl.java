package nl.fontys.sofa.limo.view.custom.pane;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.api.exception.ServiceNotFoundException;
import nl.fontys.sofa.limo.domain.SupplyChain;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.factory.WidgetFactory;
import nl.fontys.sofa.limo.view.node.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.node.HubNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegConnectionWidget;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.ReconnectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.nodes.Node;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Exceptions;

/**
 * GraphScene to draw a chain on. Widgets can be added to this scene to build
 * the chain from.
 *
 * @author Sebastiaan Heijmann
 */
public class GraphSceneImpl extends GraphScene<ContainerNode, String> {

	private final SupplyChain chain;
	private List<HubWidget> hubWidgets;
	private long edgeCounter = 0;

	// The different Layers to draw on.
	private final LayerWidget mainLayer = new LayerWidget(this);
	private final LayerWidget connectionLayer = new LayerWidget(this);
	private final LayerWidget interactionLayer = new LayerWidget(this);

	// The scene actions to be invoked.
	private final WidgetAction moveAlignAction;
	private final WidgetAction zoomAction;
	private final WidgetAction panAction;
	private final WidgetAction acceptAction;
	// The widget actions to be invoked. More actions can be found in existing
	// methods in this GraphScene class eg. createSelectAction().
	private final WidgetAction connectAction;
	private final WidgetAction reconnectAction;

	private SceneConnectManager connectionManager = new SceneConnectManager();

	/**
	 * Constructor sets up the different layers and creates basic actions which
	 * can be invoked on the scene.
	 */
	public GraphSceneImpl(SupplyChain chain) {
		this.chain = chain;
		this.hubWidgets = new ArrayList<>();

		addChild(mainLayer);
		addChild(connectionLayer);
		addChild(interactionLayer);

		moveAlignAction = ActionFactory.createAlignWithMoveAction(
				mainLayer,
				interactionLayer,
				null);
		zoomAction = ActionFactory.createZoomAction();
		panAction = ActionFactory.createPanAction();
		acceptAction = ActionFactory.createAcceptAction(
				new SceneAcceptProvider());
		connectAction = ActionFactory.createExtendedConnectAction(
				interactionLayer,
				new SceneConnectProvider());
		reconnectAction = ActionFactory.createReconnectAction(
				new SceneReconnectProvider());

		getActions().addAction(zoomAction);
		getActions().addAction(panAction);
		getActions().addAction(acceptAction);
	}

	@Override
	protected Widget attachNodeWidget(ContainerNode node) {
		Widget widget = WidgetFactory.createWidgetFromContainerNode(this, node);
		widget.getActions().addAction(createSelectAction());
		widget.getActions().addAction(createObjectHoverAction());
		widget.getActions().addAction(connectAction);
		widget.getActions().addAction(moveAlignAction);
		mainLayer.addChild(widget);
		mainLayer.repaint();
		return widget;
	}

	@Override
	protected Widget attachEdgeWidget(String edge) {
		try {
			LegTypeSelectPane inputPane = new LegTypeSelectPane();
			DialogDescriptor dd = new DialogDescriptor(inputPane, "Leg Types");
			DialogDisplayer.getDefault().notify(dd);
			List<LegType> legTypes = inputPane.getSelectedLegTypes();
			ConnectionWidget connection = new ConnectionWidget(this);
			if(legTypes.size() == 1){
				connection = new LegConnectionWidget(this, legTypes.get(0));
			}else if(legTypes.size() > 1){
				connection = new LegConnectionWidget(this, legTypes);
			}
			connection.getActions().addAction(createObjectHoverAction());
			connection.getActions().addAction(createSelectAction());
			connection.getActions().addAction(reconnectAction);
			connectionLayer.addChild(connection);
			return connection;
		} catch (ServiceNotFoundException | IntrospectionException ex) {
			Exceptions.printStackTrace(ex);
		}
		return null;
	}

	@Override
	protected void attachEdgeSourceAnchor(String edge, ContainerNode oldSourceNode, ContainerNode sourceNode) {
		Widget w = sourceNode != null ? findWidget(sourceNode) : null;
		((ConnectionWidget) findWidget(edge)).setSourceAnchor(AnchorFactory.createRectangularAnchor(w));
	}

	@Override
	protected void attachEdgeTargetAnchor(String edge, ContainerNode oldTargetNode, ContainerNode targetNode) {
		Widget w = targetNode != null ? findWidget(targetNode) : null;
		((ConnectionWidget) findWidget(edge)).setTargetAnchor(AnchorFactory.createRectangularAnchor(w));
	}

	public SupplyChain getSupplyChain() {
		return chain;
	}

	/**
	 * Accept provider for the scene. Validates if a connection can be dropped
	 * and places the connection in the scene.
	 */
	private class SceneAcceptProvider implements AcceptProvider {

		@Override
		public ConnectorState isAcceptable(Widget widget, Point point, Transferable t) {
			Node node = NodeTransfer.node(t, NodeTransfer.DND_COPY_OR_MOVE);
			if (node instanceof HubNode) {
				return ConnectorState.ACCEPT;
			}
			return ConnectorState.REJECT;
		}

		@Override
		public void accept(Widget widget, Point point, Transferable t) {
			AbstractBeanNode node = (AbstractBeanNode) NodeTransfer.node(
					t, NodeTransfer.DND_COPY_OR_MOVE);
			Widget w = GraphSceneImpl.this.addNode(new ContainerNode(node));
			w.setPreferredLocation(widget.convertLocalToScene(point));
		}
	}

	private class SceneConnectManager {

		private HubWidget sourceWidget;
		private HubWidget targetWidget;

		public HubWidget getSourceWidget() {
			return sourceWidget;
		}

		public void setSourceWidget(HubWidget sourceWidget) {
			this.sourceWidget = sourceWidget;
		}

		public HubWidget getTargetWidget() {
			return targetWidget;
		}

		public void setTargetWidget(HubWidget targetWidget) {
			this.targetWidget = targetWidget;
		}

	}

	/**
	 * SceneConnecProvider is responsible for connecting widgets together.
	 */
	private class SceneConnectProvider implements ConnectProvider {

		private ContainerNode source = null;
		private ContainerNode target = null;

		@Override
		public boolean isSourceWidget(Widget sourceWidget) {
			Object object = findObject(sourceWidget);
			source = isNode(object) ? (ContainerNode) object : null;
			return source != null;
		}

		@Override
		public boolean hasCustomTargetWidgetResolver(Scene scene) {
			return false;
		}

		@Override
		public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
			return null;
		}

		@Override
		public void createConnection(Widget sourceWidget, Widget targetWidget) {
			String edge = "edge" + edgeCounter++;
			addEdge(edge);
			setEdgeSource(edge, source);
			setEdgeTarget(edge, target);
		}

		@Override
		public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
			Object object = findObject(targetWidget);
			target = isNode(object) ? (ContainerNode) object : null;
			if (target != null) {
				return !source.equals(target) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
			}
			return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
		}

	}

	/**
	 * SceneReConnecProvider is responsible for reconnecting already connected
	 * widgets with a different widget.
	 */
	private class SceneReconnectProvider implements ReconnectProvider {

		String edge;
		ContainerNode originalNode;
		ContainerNode replacementNode;

		@Override
		public void reconnectingStarted(ConnectionWidget connectionWidget, boolean reconnectingSource) {
		}

		@Override
		public void reconnectingFinished(ConnectionWidget connectionWidget, boolean reconnectingSource) {
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
}
