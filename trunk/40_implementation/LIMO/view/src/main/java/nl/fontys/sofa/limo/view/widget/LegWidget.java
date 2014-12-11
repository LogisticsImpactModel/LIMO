package nl.fontys.sofa.limo.view.widget;

import java.awt.BasicStroke;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * Widget responsible for displaying a Leg in a GraphScene. LegWidgets can be
 * added to a connection widget.
 *
 * @author Sebastiaan Heijmann
 */
public class LegWidget extends ConnectionWidget implements BasicWidget {

    private Map<Leg, Double> legs;
    private AbstractBeanNode legNode;

    public LegWidget(Scene scene, AbstractBeanNode legNode) {
        super(scene);
        this.legNode = legNode;
        setChildLegWidgets();
        setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        setStroke(new BasicStroke(3.0f));
        setEndPointShape(PointShape.SQUARE_FILLED_BIG);
    }

    @Override
    public void addActions(ChainGraphScene scene) {
        getActions().addAction(scene.createObjectHoverAction());
        getActions().addAction(scene.createSelectAction());
//        getActions().addAction(scene.reconnectAction);
        getActions().addAction(ActionFactory.createPopupMenuAction(new LegWidget.WidgetPopupMenu()));
    }

    private void setChildLegWidgets() {
        Leg leg = getLeg();
        if (leg instanceof MultiModeLeg) {
            MultiModeLeg mml = (MultiModeLeg) leg;
            for (Map.Entry<Leg, Double> entry : legs.entrySet()) {

                ImageWidget iw = new ImageWidget(getScene());
                iw.setImage(getScaledImageFromIcon(entry.getKey().getIcon()));
                iw.getActions().addAction(ActionFactory.createPopupMenuAction(new LegWidget.WidgetPopupMenu()));

                this.setConstraint(iw, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 10);
                this.addChild(iw);
            }
        }
        if (leg instanceof ScheduledLeg) {
            ScheduledLeg sl = (ScheduledLeg) leg;

            ImageWidget defaultRoute = new ImageWidget(getScene());
            defaultRoute.setImage(getScaledImageFromIcon(sl.getAlternative().getIcon()));
            defaultRoute.getActions().addAction(ActionFactory.createPopupMenuAction(new LegWidget.WidgetPopupMenu()));

            this.setConstraint(defaultRoute, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 10);
            this.addChild(defaultRoute);

            ImageWidget iw = new ImageWidget(getScene());
            iw.setImage(getScaledImageFromIcon(sl.getAlternative().getIcon()));
            iw.getActions().addAction(ActionFactory.createPopupMenuAction(new LegWidget.WidgetPopupMenu()));

            this.setConstraint(iw, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 10);
            this.addChild(iw);
        } else {
            ImageWidget iw = new ImageWidget(getScene());
            iw.setImage(getScaledImageFromIcon(leg.getIcon()));
            iw.getActions().addAction(ActionFactory.createPopupMenuAction(new LegWidget.WidgetPopupMenu()));

            this.setConstraint(iw, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 10);
            this.addChild(iw);

        }
    }

    @Override
    public boolean drop(ChainGraphScene scene, Widget widget, Point point) {
        throw new UnsupportedOperationException("Not droppable yet");
    }

    public Leg getLeg() {
        return legNode.getLookup().lookup(Leg.class);
    }

//    @Override
//    public void setContainer(ContainerNode container) {
//        this.legNode = container;
//    }
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * The popup menu when right clicked on this widget.
     */
    private class WidgetPopupMenu implements PopupMenuProvider {

        @Override
        public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
            JPopupMenu popup = new JPopupMenu();
            popup.add(new AbstractAction("Delete") {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    ChainGraphScene scene = (ChainGraphScene) getScene();
                    scene.removeEdge(legNode);
                }
            });
            return popup;
        }
    }

    private Image getScaledImageFromIcon(Icon icon) {
        return icon.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    }
}
