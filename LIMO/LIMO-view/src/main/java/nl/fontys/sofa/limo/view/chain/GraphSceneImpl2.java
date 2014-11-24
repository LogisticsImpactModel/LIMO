package nl.fontys.sofa.limo.view.chain;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.custom.panel.SelectLegTypePanel;
import nl.fontys.sofa.limo.view.node.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.node.LegNode;
import nl.fontys.sofa.limo.view.node.WidgetableNode;
import nl.fontys.sofa.limo.view.topcomponent.ChainBuilderTopComponent;
import nl.fontys.sofa.limo.view.widget.BasicWidget;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegWidget;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Exceptions;

/**
 * GraphScene to draw a supply chain on. Widgets can be dragged and dropped to
 * this scene to build the chain from.
 *
 * @author Sebastiaan Heijmann
 */
public class GraphSceneImpl2 extends ChainGraphScene {

    private ChainBuilderTopComponent parent;
    private final ChainBuilder chainBuilder;
    private List<Widget> widgets;

    private final LayerWidget mainLayer;
    private final LayerWidget connectionLayer;
    private final LayerWidget interactionLayer;

    // The scene actions to be invoked.
    private final WidgetAction moveAlignAction;
    private final WidgetAction zoomAction;
    private final WidgetAction panAction;
    private final WidgetAction acceptAction;
    private final WidgetAction selectAction;
    private final WidgetAction connectAction;
    private final WidgetAction reconnectAction;

    public GraphSceneImpl2(ChainBuilderTopComponent parent) {
        this.parent = parent;
        chainBuilder = new ChainbuilderImpl();
        widgets = new ArrayList<>();

        this.mainLayer = new LayerWidget(this);
        this.connectionLayer = new LayerWidget(this);
        this.interactionLayer = new LayerWidget(this);

        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interactionLayer);

        moveAlignAction = ActionFactory.createAlignWithMoveAction(
                mainLayer,
                interactionLayer,
                null);
        zoomAction = ActionFactory.createZoomAction();
        panAction = ActionFactory.createPanAction();
        acceptAction = ActionFactory.createAcceptAction(new SceneAcceptProvider(this));
        selectAction = ActionFactory.createSelectAction(new SceneSelectProvider());
        connectAction = ActionFactory.createExtendedConnectAction(interactionLayer, new SceneConnectProvider());
        reconnectAction = null;

        getActions().addAction(acceptAction);
        getActions().addAction(zoomAction);
        getActions().addAction(panAction);
    }

    @Override
    public List<Widget> getWidgets() {
        return widgets;
    }

    @Override
    public LayerWidget getMainLayer() {
        return mainLayer;
    }

    @Override
    public LayerWidget getConnectionLayer() {
        return connectionLayer;
    }

    @Override
    public WidgetAction getSelectAction() {
        return selectAction;
    }

    @Override
    public WidgetAction getConnectAction() {
        return connectAction;
    }

    @Override
    public WidgetAction getMoveAlignAction() {
        return moveAlignAction;
    }

    @Override
    protected Widget attachNodeWidget(ContainerNode node) {
        BasicWidget widget = (BasicWidget) node.getWidget(this);
        widget.addActions(this);
        return (Widget) widget;
    }

    @Override
    protected Widget attachEdgeWidget(ContainerNode edge) {
        BasicWidget connectionWidget = new LegWidget(this, edge);
        connectionWidget.addActions(this);
        connectionLayer.addChild((Widget) connectionWidget);
        return (Widget) connectionWidget;
    }

    @Override
    protected void attachEdgeSourceAnchor(ContainerNode edge, ContainerNode oldSourceNode, ContainerNode sourceNode) {
        Widget sourceWidget = findWidget(sourceNode);
        if (sourceWidget == null) {
            sourceWidget = findWidget(oldSourceNode);
        }
        ConnectionWidget connectionWidget = (ConnectionWidget) findWidget(edge);
        connectionWidget.setSourceAnchor(AnchorFactory.createRectangularAnchor(sourceWidget));
    }

    @Override
    protected void attachEdgeTargetAnchor(ContainerNode edge, ContainerNode oldTargetNode, ContainerNode targetNode) {
        Widget targetWidget = findWidget(targetNode);
        if (targetWidget == null) {
            targetWidget = (HubWidget) findWidget(oldTargetNode);
        }
        ConnectionWidget connectionWidget = (ConnectionWidget) findWidget(edge);
        connectionWidget.setTargetAnchor(AnchorFactory.createRectangularAnchor(targetWidget));
    }

    @Override
    public void repaintScene() {
        validate();
        getMainLayer().repaint();
        getConnectionLayer().repaint();
    }

    /**
     * Accept provider for the scene. Validates if a connection can be dropped
     * and places the connection in the scene.
     */
    private class SceneAcceptProvider implements AcceptProvider {

        private final ChainGraphScene scene;

        public SceneAcceptProvider(ChainGraphScene scene) {
            this.scene = scene;
        }

        @Override
        public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
            WidgetableNode node = (WidgetableNode) NodeTransfer.node(transferable, NodeTransfer.DND_COPY_OR_MOVE);
            if (node.isAcceptable(widget, point)) {
                return ConnectorState.ACCEPT;
            }
            return ConnectorState.REJECT;
        }

        @Override
        public void accept(Widget widget, Point point, Transferable transferable) {
            AbstractBeanNode node = (AbstractBeanNode) NodeTransfer.node(transferable, NodeTransfer.DND_COPY_OR_MOVE);
            BasicWidget w = (BasicWidget) scene.addNode(new ContainerNode(node));
            boolean succesfullDrop = w.drop(scene, chainBuilder, widget, point);
            if (succesfullDrop) {
                widgets.add((Widget) w);
            }
        }
    }

    private class SceneSelectProvider implements SelectProvider {

        @Override
        public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        @Override
        public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
            return findObject(widget) != null;
        }

        @Override
        public void select(Widget widget, Point localLocation, boolean invertSelection) {
            Object object = findObject(widget);
            ContainerNode container = (ContainerNode) object;
            parent.setRootConttext(container.getBeanNode());

            setFocusedObject(object);
            if (object != null) {
                if (!invertSelection && getSelectedObjects().contains(object)) {
                    return;
                }
                userSelectionSuggested(Collections.singleton(object), invertSelection);
            } else {
                userSelectionSuggested(Collections.emptySet(), invertSelection);
            }
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
            ContainerNode container = (ContainerNode) findObject(sourceWidget);
            source = isNode(container) ? container : null;
            return source != null;
        }

        @Override
        public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
            ContainerNode container = (ContainerNode) findObject(targetWidget);
            target = isNode(container) ? container : null;
            if (target != null) {
                return !source.equals(target) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
            }
            return container != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
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
            SelectLegTypePanel inputPane = new SelectLegTypePanel();
            Leg leg = inputPane.getLeg();
            if (leg != null) {
                try {
                    LegNode legNode = new LegNode(leg);
                    ContainerNode container = new ContainerNode(legNode);

                    Widget connectionWidget = addEdge(container);
                    if (connectionWidget != null) {
                        setEdgeSource(container, source);
                        setEdgeTarget(container, target);
                    }
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }

        }
    }
}
