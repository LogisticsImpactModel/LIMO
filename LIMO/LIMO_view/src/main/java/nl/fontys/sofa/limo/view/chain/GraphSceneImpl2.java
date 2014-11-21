package nl.fontys.sofa.limo.view.chain;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import nl.fontys.sofa.limo.view.node.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.node.WidgetableNode;
import nl.fontys.sofa.limo.view.widget.BasicWidget;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.openide.nodes.NodeTransfer;
import java.util.ArrayList;
import java.util.List;

/**
 * GraphScene to draw a supply chain on. Widgets can be dragged and dropped to
 * this scene to build the chain from.
 *
 * @author Sebastiaan Heijmann
 */
public class GraphSceneImpl2 extends ChainGraphScene {

    private List<BasicWidget> widgets;

    private final LayerWidget mainLayer;
    private final LayerWidget connectionLayer;
    private final LayerWidget interactionLayer;

    // The scene actions to be invoked.
    private final WidgetAction moveAlignAction;
    private final WidgetAction zoomAction;
    private final WidgetAction panAction;
    private final WidgetAction acceptAction;
    private final WidgetAction connectAction;
    private final WidgetAction reconnectAction;

    public GraphSceneImpl2() {
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
        connectAction = null;
        reconnectAction = null;

        getActions().addAction(acceptAction);
        getActions().addAction(zoomAction);
        getActions().addAction(panAction);
    }

    @Override
    public List<BasicWidget> getWidgets() {
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
    protected Widget attachEdgeWidget(String edge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeSourceAnchor(String edge, ContainerNode oldSourceNode, ContainerNode sourceNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeTargetAnchor(String edge, ContainerNode oldTargetNode, ContainerNode targetNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            boolean succesfullDrop = w.drop(scene, widget, point);
            if (succesfullDrop) {
                widgets.add(w);
            }
        }

    }
}