package nl.fontys.sofa.limo.view.widget;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.api.service.provider.ProcedureService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.util.undoable.events.EventUndoableEdit;
import nl.fontys.sofa.limo.view.util.undoable.widget.hub.DeleteHubWidgetUndoableEdit;
import nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.SeparatorWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Lookup;

/**
 * HubWidget which represents a Hub in the GraphScene. It holds HubNode which
 * contains a Hub.
 *
 * @author Sebastiaan Heijmann
 */
public final class HubWidget extends IconNodeWidget implements BasicWidget {

    private final int widgetWidth = 140;
    private final int widgetHeight = 170;
    private final Color backgroundColor = new Color(0, 0, 0, 0);
    private final HubNode hubNode;
    private Widget containerWidget;
    private LabelWidget eventLabelWidget;
    private LabelWidget procedureLabelWidget;
    private final Widget startFlagWidget;

    /**
     * Constructor sets up the widget by setting the display name and image.
     *
     * @param scene - the scene to display the Widget on.
     * @param beanNode - the beanNode belonging to this widget.
     */
    public HubWidget(Scene scene, HubNode beanNode) throws IOException {
        super(scene);
        this.hubNode = beanNode;
        setPreferredBounds(new Rectangle(widgetWidth, widgetHeight));
        setPreferredSize(new Dimension(widgetWidth, widgetHeight));
        setToolTipText(hubNode.getName());
        setOpaque(false);
        startFlagWidget = new StartWidget(scene);
        startFlagWidget.setVisible(false);
        setImage(getHub().getIcon().getImage());
        setLabel(beanNode.getName());
        addSeparator();
        addChildren();
        beanNode.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {

                listeners.forEach((PropertyChangeListener t) -> {
                    t.propertyChange(evt);
                });
            }
        });
    }

    @Override
    public void addActions(ChainGraphScene scene) {
        getActions().addAction(scene.getSelectAction());
        getActions().addAction(scene.createObjectHoverAction());
        getActions().addAction(scene.getConnectAction());
        getActions().addAction(scene.getMoveAlignAction());
        getActions().addAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenu()));
    }

    private List<PropertyChangeListener> listeners = new ArrayList<>();

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Add the children to this widget.
     */
    private void addChildren() {
        containerWidget = new Widget(getScene());
        containerWidget.setLayout(LayoutFactory.createHorizontalFlowLayout());

        addChild(containerWidget);
        procedureLabelWidget = new LabelWidget(getScene(), "Procedures: " + getHub().getProcedures().size());
        procedureLabelWidget.getActions().addAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenu()));
        this.addChild(procedureLabelWidget);

        eventLabelWidget = new LabelWidget(getScene());
        eventLabelWidget.getActions().addAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenu()));

        if (getHub().getEvents() != null && !getHub().getEvents().isEmpty()) {
            eventLabelWidget = new LabelWidget(getScene(), "Events: " + getHub().getEvents().size());
        }
        this.addChild(eventLabelWidget);
        addChild(startFlagWidget);
    }

    /**
     * Add a separator to this widget.
     */
    private void addSeparator() {
        SeparatorWidget separatorWidget = new SeparatorWidget(getScene(), SeparatorWidget.Orientation.HORIZONTAL);
        separatorWidget.setThickness(10);
        addChild(separatorWidget);
    }

    /**
     * Create a border around this widget.
     */
    private void createBorder() {
        setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(
                        BorderFactory.createEmptyBorder(),
                        hubNode.getName(),
                        TitledBorder.CENTER,
                        TitledBorder.ABOVE_TOP),
                BorderFactory.createLineBorder(backgroundColor)));
    }

    @Override
    public boolean drop(ChainGraphScene scene, Widget widget, Point point) {
        this.setPreferredLocation(point);
        if (scene.getStartWidget() == null) {
            scene.setStartWidget(this);
        }
        scene.addHubWidget(this);
        repaint();
        return true;
    }

    /**
     * Get the hub which belongs to this widget.
     *
     * @return
     */
    public Hub getHub() {
        return hubNode.getLookup().lookup(Hub.class);
    }

    public HubWidget getHubWidget() {
        return this;
    }

    /**
     * Update the widgets properties and hide or show the procedures or events
     * icon.
     *
     * @param pce the event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        Hub hub = getHub();
        setImage(hub.getIcon().getImage());
        setLabel(hub.getName());
        setToolTipText(hub.getName());

        if (pce.getPropertyName().equals("events")) {
            ChainGraphScene scene = (ChainGraphScene) getScene();
            UndoManager manager = scene.getLookup().lookup(UndoManager.class);
            manager.undoableEditHappened(new UndoableEditEvent(this, new EventUndoableEdit(hubNode, scene, eventLabelWidget)));
        }
        updateLabels();

    }

    public void updateLabels() {

        procedureLabelWidget.setLabel("Procedure: " + getHub().getProcedures().size());

        if (getHub().getEvents().isEmpty()) {
            eventLabelWidget.setLabel("");
        } else {
            eventLabelWidget.setLabel("Events: " + getHub().getEvents().size());
        }
    }

    /**
     * Set the start flag visible for this HubWidget.
     *
     * @param startFlag - true if start flag should be visible.
     */
    public void setStartFlag(boolean startFlag) {
        startFlagWidget.setVisible(startFlag);
    }

    /**
     * The pop up menu when right clicked on this widget. The offered actions
     * are the selection of the widget to be a start hub or deleting the widget.
     */
    private class WidgetPopupMenu implements PopupMenuProvider {

        @Override
        public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
            JPopupMenu popup = new JPopupMenu();
            popup.add(new AbstractAction(LIMOResourceBundle.getString("SET_START_HUB")) {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    ChainGraphScene scene = (ChainGraphScene) getScene();
                    scene.setStartWidget(HubWidget.this);
                }
            });

            popup.add(new AbstractAction(LIMOResourceBundle.getString("EDIT")) {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    HubWizardAction wiz = new HubWizardAction();
                    wiz.setUpdate(getHub());
                    wiz.actionPerformed(ae);

                    propertyChange(null);
                }
            });
            popup.add(new AbstractAction(LIMOResourceBundle.getString("DELETE")) {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    ChainGraphScene scene = (ChainGraphScene) getScene();
                    UndoManager manager = scene.getLookup().lookup(UndoManager.class);
                    // add a new UndoableEditEvent to the undoManager of the ChainGraphScene when the undoManager exists
                    if (manager != null) {
                        manager.undoableEditHappened(new UndoableEditEvent(HubWidget.this, new DeleteHubWidgetUndoableEdit(scene, HubWidget.this)));
                    }
                    scene.removeHubWidget(HubWidget.this);
                    scene.removeNodeWithEdges(hubNode);
                    propertyChange(null);
                }
            });
            JMenu procedureMenu = new JMenu("Proceduren");
            ProcedureService procedureService = Lookup.getDefault().lookup(ProcedureService.class);
            List<Procedure> procedureList = procedureService.findAll();
            procedureList.stream().forEach((procedure) -> {
                procedureMenu.add(new AbstractAction(procedure.getName()) {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        List<Procedure> procedureListOfHub = HubWidget.this.getHub().getProcedures();
                        Procedure selected = procedureService.findById(procedure.getId());
                        selected.setId(null);
                        procedureListOfHub.add(selected);
                        HubWidget.this.getHub().setProcedures(procedureListOfHub);
                        updateLabels();
                    }
                });
            });
            JMenu eventMenu = new JMenu("Events");
            EventService eventService = Lookup.getDefault().lookup(EventService.class);
            List<Event> eventList = eventService.findAll();
            eventList.stream().forEach((event) -> {
                eventMenu.add(new AbstractAction(event.getName()) {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        List<Event> eventListOfHub = HubWidget.this.getHub().getEvents();
                        Event selected = eventService.findById(event.getId());
                        selected.setId(null);
                        eventListOfHub.add(selected);
                        HubWidget.this.getHub().setEvents(eventListOfHub);
                        updateLabels();
                    }
                });
            });
            popup.add(eventMenu);
            return popup;
        }
    }
}
