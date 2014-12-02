package nl.fontys.sofa.limo.view.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JPopupMenu;
import javax.swing.border.TitledBorder;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.HubNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.SeparatorWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 * HubWidget which represents a Hub in the GraphScene. It holds HubNode which
 * contains a Hub.
 *
 * @author Sebastiaan Heijmann
 */
public final class HubWidget extends IconNodeWidget implements BasicWidget {

    private final int widgetWidth = 140;
    private final int widgetHeight = 170;
    private Color backgroundColor = Color.WHITE;

    private final HubNode hubNode;

    private Widget eventWidget;
    private Widget procedureWidget;
    private final Widget startFlagWidget;

    /**
     * Constructor sets up the widget by setting the display name and image.
     *
     * @param scene - the scene to display the Widget on.
     */
    public HubWidget(Scene scene, HubNode beanNode) throws IOException {
        super(scene);
        this.hubNode = beanNode;

        setPreferredBounds(new Rectangle(widgetWidth, widgetHeight));
        setPreferredSize(new Dimension(widgetWidth, widgetHeight));
//        setBackground(backgroundColor);
        setToolTipText(hubNode.getName());
        setOpaque(false);

        startFlagWidget = new StartWidget(scene);
        startFlagWidget.setVisible(false);

        setImage(getHub().getIcon().getImage());
        createBorder();
        addSeparator();
        addChildren();
    }

    @Override
    public void addActions(ChainGraphScene scene) {
        getActions().addAction(scene.getSelectAction());
        getActions().addAction(scene.createObjectHoverAction());
        getActions().addAction(scene.getConnectAction());
        getActions().addAction(scene.getMoveAlignAction());
        getActions().addAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenu()));
    }

    private void addChildren() {
        Scene scene = getScene();

        Widget widget = new Widget(scene);
        widget.setLayout(LayoutFactory.createHorizontalFlowLayout());
        widget.addChild(new EventsWidget(scene));
        widget.addChild(new ProcedureWidget(scene));

        addChild(widget);

        addChild(startFlagWidget);
    }

    private void addSeparator() {
        SeparatorWidget separatorWidget = new SeparatorWidget(getScene(), SeparatorWidget.Orientation.HORIZONTAL);
        separatorWidget.setThickness(10);
        addChild(separatorWidget);
    }

    private void createBorder() {
        setBorder(BorderFactory.createCompoundBorder(
                new TitledBorder(
                        BorderFactory.createEmptyBorder(),
                        hubNode.getName(),
                        TitledBorder.CENTER,
                        TitledBorder.ABOVE_TOP),
                BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 10)));
    }

    @Override
    public boolean drop(ChainGraphScene scene, Widget widget, Point point) {
        this.setPreferredLocation(point);
        if (scene.getStartHubWidget() == null) {
            scene.setStartHubWidget(this);
        }
        scene.addHubWidget(this);
        repaint();
        return true;
    }

    public Hub getHub() {
        return hubNode.getLookup().lookup(Hub.class);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        setImage(getHub().getIcon().getImage());
        repaint();
    }

    /**
     * Revalidate and repaint this Widget.
     */
    public void validateHubWidget() {
        Hub hub = getHub();
        if (hub.getEvents().isEmpty()) {
            eventWidget.setVisible(false);
        } else {
            eventWidget.setVisible(true);
        }
        if (hub.getProcedures().isEmpty()) {
            procedureWidget.setVisible(false);
        } else {
            procedureWidget.setVisible(true);
        }
        repaint();
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
     * The popup menu when right clicked on this widget.
     */
    private class WidgetPopupMenu implements PopupMenuProvider {

        @Override
        public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
            JPopupMenu popup = new JPopupMenu();
            popup.add(new AbstractAction("Set Start Hub") {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    ChainGraphScene scene = (ChainGraphScene) getScene();
                    scene.setStartHubWidget(HubWidget.this);
                }
            });
            popup.add(new AbstractAction("Delete") {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    ChainGraphScene scene = (ChainGraphScene) getScene();
                    scene.removeHubWidget(HubWidget.this);
                    scene.removeNodeWithEdges(hubNode);
                }
            });
            return popup;
        }
    }

}
