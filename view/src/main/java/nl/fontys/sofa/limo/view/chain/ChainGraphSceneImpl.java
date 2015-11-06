package nl.fontys.sofa.limo.view.chain;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager;
import nl.fontys.sofa.limo.api.service.status.StatusBarService;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.custom.panel.SelectLegTypePanel;
import nl.fontys.sofa.limo.view.node.WidgetableNode;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.bean.EventNode;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.node.bean.LegNode;
import nl.fontys.sofa.limo.view.node.bean.MultiModeLegNode;
import nl.fontys.sofa.limo.view.node.bean.ProcedureNode;
import nl.fontys.sofa.limo.view.node.bean.ScheduledLegNode;
import nl.fontys.sofa.limo.view.topcomponent.DynamicExplorerManagerProvider;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.util.undoable.widget.hub.AddHubWidgetUndoableEdit;
import nl.fontys.sofa.limo.view.util.undoable.widget.leg.AddLegWidgetUndoableEdit;
import nl.fontys.sofa.limo.view.widget.BasicWidget;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegWidget;
import nl.fontys.sofa.limo.view.widget.StartWidget;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.nodes.NodeTransfer;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

/**
 * Implementation of the {@link nl.fontys.sofa.limo.view.chain.ChainGraphScene}
 * interface.
 * <p>
 * This scene has 3 different {@link org.netbeans.api.visual.widget.LayerWidget}
 * to draw things on and provides several
 * {@link org.netbeans.api.visual.action.WidgetAction}. It also uses a
 * {@link nl.fontys.sofa.limo.view.chain.ChainBuilder} which manages the correct
 * building of the {@link nl.fontys.sofa.limo.domain.component.SupplyChain}.
 * <p>
 * Some actions need a provider implementation which is implemented in several
 * private classes within this class.
 * <p>
 * The {@link nl.fontys.sofa.limo.view.chain.ChainGraphSceneImpl} creates
 * {@link javax.swing.undo.UndoableEdit} for add and deleting a
 * {@link nl.fontys.sofa.limo.view.widget.LegWidget} or
 * {@link nl.fontys.sofa.limo.view.widget.HubWidget}. When a HubWidget is
 * deleted a undoableEdit is created and added to the UndoManager which is hand
 * in the constructor. The same belongs to add HubWidget and delete and add
 * LegWidget.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainGraphSceneImpl extends ChainGraphScene {

    private final DynamicExplorerManagerProvider parent;
    private final ChainBuilder chainBuilder;
    private final SupplyChain loadedChain;

    // The layers to draw things on.
    private final LayerWidget mainLayer;
    private final LayerWidget connectionLayer;
    private final LayerWidget interactionLayer;

    // The scene actions available.
    private final WidgetAction moveAlignAction;
    private final WidgetAction zoomAction;
    private final WidgetAction panAction;
    private final WidgetAction acceptAction;
    private final WidgetAction selectAction;
    private final WidgetAction connectAction;
    //@TODO implement reconnect action
    private final WidgetAction reconnectAction;

    private HubWidget startHubWidget;
    private final StartWidget startFlagWidget;

    private UndoManager undoManager;
    private ProxyLookup lookup;

    /**
     * Constructor which sets the parent and creates the chain builder, the
     * layers and the available actions.
     *
     * @param parent the parent of this scene.
     * @param chain
     * @throws IOException can occur when certain resources like images cannot
     * @throws IntrospectionException be found.
     */
    public ChainGraphSceneImpl(DynamicExplorerManagerProvider parent, SupplyChain chain) throws IOException, IntrospectionException {
        this(parent, chain, null);
    }

    /**
     * Constructor which sets the parent and creates the chain builder, the
     * layers, the available actions and using a undoManager.
     *
     * @param parent the parent of this scene.
     * @param chain
     * @param undoManager undo manager which should be used to undo changes
     * @throws IOException can occur when certain resources like images cannot
     * @throws IntrospectionException be found.
     */
    public ChainGraphSceneImpl(DynamicExplorerManagerProvider parent, SupplyChain chain, UndoManager undoManager) throws IOException, IntrospectionException {
        this.parent = parent;
        chainBuilder = new ChainBuilderImpl();
        chainBuilder.getSupplyChain().setName(chain.getName()); //sets the name of 
        //the supplyChain so that when you load an existing supplychain and 
        //then save it at another location dont get a file named null.lsc
        chainBuilder.getSupplyChain().setFilepath(chain.getFilepath());
        loadedChain = chain;

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

        startFlagWidget = new StartWidget(this);

        this.undoManager = undoManager;

        if (undoManager != null) {
            Lookup undoRedo = Lookups.singleton(undoManager);
            lookup = new ProxyLookup(undoRedo, super.getLookup());
        } else {
            lookup = (ProxyLookup) super.getLookup();

        }

    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public JComponent createView() {
        JComponent component = super.createView();
        if (loadedChain.getStartHub() != null) {
            try {
                drawExistingSupplyChain(loadedChain);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return component;
    }

    /**
     * Draw an existing supplychain onto the scene.
     *
     * @param supplyChain the supplychain to draw.
     * @throws IntrospectionException
     */
    void drawExistingSupplyChain(SupplyChain supplyChain) throws IntrospectionException {
        Point point = new Point(100, 100);
        mainLayer.setLayout(LayoutFactory.createAbsoluteLayout());
        Node currentNode = supplyChain.getStartHub();

        HubWidget previousWidget = null;
        ConnectionWidget connectionWidget = null;
        HubWidget nextWidget = null;

        while (currentNode != null) {
            if (currentNode instanceof Hub) {
                HubWidget w = (HubWidget) addNode(new HubNode((Hub) currentNode));
                addHubWidget(w);
                if (currentNode == supplyChain.getStartHub()) {
                    setStartWidget(w);
                }
                w.setPreferredLocation(point);
                nextWidget = w;
                point = new Point(((int) point.getX() + 300), (int) point.getY());

                if (connectionWidget != null) {
                    connectHubWidgets(previousWidget, connectionWidget, nextWidget);
                }
            } else if (currentNode instanceof Leg) {
                if (currentNode instanceof ScheduledLeg) {
                    connectionWidget = (ConnectionWidget) addEdge(new ScheduledLegNode((ScheduledLeg) currentNode));
                } else if (currentNode instanceof MultiModeLeg) {
                    connectionWidget = (ConnectionWidget) addEdge(new MultiModeLegNode((MultiModeLeg) currentNode));
                } else if (currentNode instanceof Leg) {
                    connectionWidget = (ConnectionWidget) addEdge(new LegNode((Leg) currentNode));
                }
                previousWidget = nextWidget;
            }
            currentNode = currentNode.getNext();
        }
    }

    @Override
    public ChainBuilder getChainBuilder() {
        return chainBuilder;
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
    public SupplyChain getSupplyChain() {
        return chainBuilder.getSupplyChain();
    }

    @Override
    public DynamicExplorerManagerProvider getParent() {
        return parent;
    }

    @Override
    public Widget getStartWidget() {
        return startHubWidget;
    }

    @Override
    public void setStartWidget(Widget widget) {
        if (startHubWidget != null) {
            startHubWidget.setStartFlag(false);
        }

        HubWidget hubWidget = (HubWidget) widget;
        startHubWidget = hubWidget;
        startHubWidget.setStartFlag(true);

        chainBuilder.setStartHub(hubWidget.getHub());
        this.validate();
    }

    @Override
    public void addHubWidget(HubWidget hubWidget) {
        Lookup.getDefault().lookup(StatusBarService.class).setMessage(hubWidget.getHub().getName(), StatusBarService.ACTION_ADD, StatusBarService.STATE_SUCCESS, null);
        if (hubWidget.getHub().getPrevious() == null) {
            hubWidget.setStartFlag(true);
        }
        mainLayer.addChild(hubWidget);
        chainBuilder.addHub(hubWidget.getHub());
        checkChainHubs();
        getScene().repaint();

    }

    @Override
    public void connectHubWidgets(HubWidget source, ConnectionWidget legWidget, HubWidget target) {
        Lookup.getDefault().lookup(StatusBarService.class).setMessage(source.getHub().getName() + " " + LIMOResourceBundle.getString("AND") + " " + target.getHub().getName(), StatusBarService.ACTION_CONNECT, StatusBarService.STATE_SUCCESS, null);
        AbstractBeanNode sourceNode = (AbstractBeanNode) findObject(source);
        AbstractBeanNode legNode = (AbstractBeanNode) findObject(legWidget);
        AbstractBeanNode targetNode = (AbstractBeanNode) findObject(target);

        setEdgeSource(legNode, sourceNode);
        setEdgeTarget(legNode, targetNode);

        Hub hubSource = sourceNode.getLookup().lookup(Hub.class);
        Leg leg = legNode.getLookup().lookup(Leg.class);
        Hub hubTarget = targetNode.getLookup().lookup(Hub.class);

        chainBuilder.connectHubsByLeg(
                hubSource,
                leg,
                hubTarget);
        target.setStartFlag(false);
        this.checkChainHubs();
    }

    /**
     * Checks the hubs and hubWidgets if the previous Node is null and sets the
     * starthub.
     */
    public void checkChainHubs() {
        int checkIfMoreThanOne = 0;
        Hub startHub = new Hub();
        List<Hub> hubList = chainBuilder.getHubList();
        for (Hub hub : hubList) {
            if (hub.getPrevious() == null) {
                checkIfMoreThanOne++;
                startHub = hub;
            }
        }
        if (checkIfMoreThanOne == 1) {
            chainBuilder.setStartHub(startHub);
        }

        List<Widget> hubWidgetList = mainLayer.getChildren();
        for (Widget hubWidget : hubWidgetList) {
            HubWidget hw = (HubWidget) hubWidget;
            hw.setStartFlag(hw.getHub().getPrevious() == null);
        }
    }

    @Override
    public void removeHubWidget(HubWidget hubWidget) {
        Hub hub = hubWidget.getHub();
        chainBuilder.removeHub(hubWidget.getHub());

        if (chainBuilder.getStartHub() == hub) {
            chainBuilder.setStartHub(null);
        }

    }

    @Override
    public void disconnectLegWidget(LegWidget legWidget) {
        Lookup.getDefault().lookup(StatusBarService.class).setMessage(legWidget.getLeg().getName(),
                StatusBarService.ACTION_DELETE, StatusBarService.STATE_SUCCESS, null);
        chainBuilder.disconnectLeg(legWidget.getLeg());
        this.checkChainHubs();
    }

    @Override
    public int getNumberOfHubs() {
        return chainBuilder.getNumberOfHubs();
    }

    @Override
    protected Widget attachNodeWidget(AbstractBeanNode node) {
        WidgetableNode wn = (WidgetableNode) node;
        BasicWidget widget = (BasicWidget) wn.getWidget(this);
        widget.addActions(this);
        return (Widget) widget;
    }

    @Override
    protected Widget attachEdgeWidget(AbstractBeanNode edge) {
        BasicWidget connectionWidget = new LegWidget(this, edge);
        connectionWidget.addActions(this);
        connectionLayer.addChild((Widget) connectionWidget);
        return (Widget) connectionWidget;
    }

    @Override
    protected void attachEdgeSourceAnchor(AbstractBeanNode edge, AbstractBeanNode oldSourceNode, AbstractBeanNode sourceNode) {
        Widget sourceWidget = findWidget(sourceNode);
        if (sourceWidget == null) {
            sourceWidget = findWidget(oldSourceNode);
        }
        ConnectionWidget connectionWidget = (ConnectionWidget) findWidget(edge);
        connectionWidget.setSourceAnchor(AnchorFactory.createRectangularAnchor(sourceWidget, false));
    }

    @Override
    protected void attachEdgeTargetAnchor(AbstractBeanNode edge, AbstractBeanNode oldTargetNode, AbstractBeanNode targetNode) {
        Widget targetWidget = findWidget(targetNode);
        if (targetWidget == null) {
            targetWidget = (HubWidget) findWidget(oldTargetNode);
        }
        ConnectionWidget connectionWidget = (ConnectionWidget) findWidget(edge);
        connectionWidget.setTargetAnchor(AnchorFactory.createRectangularAnchor(targetWidget, false));
    }

    /**
     * This provider is responsible for accepting objects into the scene.
     */
    private class SceneAcceptProvider implements AcceptProvider {

        private final ChainGraphScene scene;

        /**
         * Constructor sets the scene.
         *
         * @param scene the main scene.
         */
        public SceneAcceptProvider(ChainGraphScene scene) {
            this.scene = scene;
        }

        @Override
        public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
            org.openide.nodes.Node node = NodeTransfer.node(transferable, NodeTransfer.DND_COPY_OR_MOVE);

            if (node instanceof WidgetableNode) {
                WidgetableNode widgetNode = (WidgetableNode) node;
                if (widgetNode.isAcceptable(widget, point)) {
                    return ConnectorState.ACCEPT;
                }
            } else if (node instanceof EventNode) {
                return ConnectorState.ACCEPT;
            } else if (node instanceof ProcedureNode) {
                return ConnectorState.ACCEPT;
            }
            return ConnectorState.REJECT;
        }

        @Override
        public void accept(Widget widget, Point point, Transferable transferable) {
            AbstractBeanNode node = (AbstractBeanNode) NodeTransfer.node(transferable, NodeTransfer.DND_COPY_OR_MOVE);

            if (node instanceof EventNode) {
                acceptEvent(node, point);
            } else if (node instanceof ProcedureNode) {
                acceptProcedure(node, point);
            } else {
                acceptHub(node, widget, point);
            }
            TopComponent comp = (TopComponent) parent;
            comp.requestActive();
        }

        private void acceptHub(AbstractBeanNode node, Widget widget, Point point) {
            AbstractBeanNode detachedNode = node.getDetachedNodeCopy();
            BasicWidget w = (BasicWidget) scene.addNode(detachedNode);
            detachedNode.addPropertyChangeListener(w);
            w.drop(scene, widget, point);

            if (undoManager != null) {
                UndoableEditEvent event = new UndoableEditEvent(w, new AddHubWidgetUndoableEdit(scene, (HubWidget) w));
                undoManager.undoableEditHappened(event);
            }
            TopComponent comp = (TopComponent) parent;
            comp.requestActive();

        }

        private void acceptEvent(AbstractBeanNode node, Point point) {
            Event event = node.getLookup().lookup(Event.class);
            List<Widget> hitlist = new ArrayList<>();
            mainLayer.getChildren().forEach((w) -> {
                Point p = w.convertSceneToLocal(point);
                if (w.isHitAt(p)) {
                    hitlist.add(w);
                }
            });
            connectionLayer.getChildren().forEach((c) -> {
                Point p = c.convertSceneToLocal(point);
                Rectangle r = new Rectangle(p);
                r.width = 25;
                r.height = 25;
                r.x -= r.width / 2;
                r.y -= r.height / 2;

                if (c.getBounds().contains(r)) {
                    hitlist.add(c);
                }
            });

            hitlist.forEach((w) -> {
                if (w instanceof HubWidget) {
                    HubWidget hubWidget = (HubWidget) w;
                    hubWidget.getHub().getEvents().add(event);
                    hubWidget.updateLabels();
                } else if (w instanceof LegWidget) {
                    LegWidget legWidget = (LegWidget) w;
                    legWidget.getLeg().getEvents().add(event);
                    legWidget.updateLabels();
                }
            });
            mainLayer.revalidate();
            connectionLayer.revalidate();
        }
        
        private void acceptProcedure(AbstractBeanNode node, Point point) {
            Procedure procedure = node.getLookup().lookup(Procedure.class);
            List<Widget> hitlist = new ArrayList<>();
            mainLayer.getChildren().forEach((w) -> {
                Point p = w.convertSceneToLocal(point);
                if (w.isHitAt(p)) {
                    hitlist.add(w);
                }
            });
            connectionLayer.getChildren().forEach((c) -> {
                Point p = c.convertSceneToLocal(point);
                Rectangle r = new Rectangle(p);
                r.width = 25;
                r.height = 25;
                r.x -= r.width / 2;
                r.y -= r.height / 2;

                if (c.getBounds().contains(r)) {
                    hitlist.add(c);
                }
            });

            hitlist.forEach((w) -> {
                if (w instanceof HubWidget) {
                    HubWidget hubWidget = (HubWidget) w;
                    hubWidget.getHub().getProcedures().add(procedure);
                    hubWidget.updateLabels();
                } else if (w instanceof LegWidget) {
                    LegWidget legWidget = (LegWidget) w;
                    legWidget.getLeg().getProcedures().add(procedure);
                    legWidget.updateLabels();
                }
            });
            mainLayer.revalidate();
            connectionLayer.revalidate();
        }
    }

    /**
     * This provider is responsible for selecting objects in a scene.
     */
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
            AbstractBeanNode container = (AbstractBeanNode) object;
            parent.setRootContext(container);

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
     * This provider is responsible for connecting objects in a scene.
     */
    private class SceneConnectProvider implements ConnectProvider {

        private AbstractBeanNode source = null;
        private AbstractBeanNode target = null;

        @Override
        public boolean isSourceWidget(Widget sourceWidget) {
            AbstractBeanNode container = (AbstractBeanNode) findObject(sourceWidget);
            source = isNode(container) ? container : null;
            return source != null;
        }

        @Override
        public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
            AbstractBeanNode container = (AbstractBeanNode) findObject(targetWidget);
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
            if (validateConnection(sourceWidget, targetWidget)) {

                SelectLegTypePanel inputPane = new SelectLegTypePanel();
                Leg leg = inputPane.getLeg();

                if (leg != null) {
                    try {

                        HubWidget hubSourceWidget = (HubWidget) findWidget(source);
                        HubWidget hubTargetWidget = (HubWidget) findWidget(target);

                        ConnectionWidget connectionWidget;
                        if (leg instanceof ScheduledLeg) {
                            connectionWidget
                                    = (ConnectionWidget) addEdge(new ScheduledLegNode((ScheduledLeg) leg));
                        } else if (leg instanceof MultiModeLeg) {
                            connectionWidget
                                    = (ConnectionWidget) addEdge(new MultiModeLegNode((MultiModeLeg) leg));
                        } else {
                            connectionWidget
                                    = (ConnectionWidget) addEdge(new LegNode((Leg) leg));
                        }

                        connectHubWidgets(
                                hubSourceWidget,
                                connectionWidget,
                                hubTargetWidget);
                        if (undoManager != null) {
                            undoManager.undoableEditHappened(new UndoableEditEvent(connectionWidget, new AddLegWidgetUndoableEdit((LegWidget) connectionWidget, (HubNode) source, (HubNode) target, ChainGraphSceneImpl.this)));
                        }
                    } catch (IntrospectionException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                }
            }
        }

        /**
         * Validate a connection between two widgets.
         *
         * @param sourceWidget the connection source.
         * @param targetWidget the connection target.
         * @return
         */
        private boolean validateConnection(
                Widget sourceWidget,
                Widget targetWidget) {

            AbstractBeanNode sourceNode
                    = (AbstractBeanNode) findObject(sourceWidget);
            AbstractBeanNode targetNode
                    = (AbstractBeanNode) findObject(targetWidget);

            if (findNodeEdges(sourceNode, true, false).isEmpty()
                    && findNodeEdges(targetNode, false, true).isEmpty()) {
                return true;
            }
            return false;
        }
    }
}
