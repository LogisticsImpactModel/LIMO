package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * Widget responsible for displaying a Leg in a GraphScene. LegWidgets can be
 * added to a connection widget.
 *
 * @author Sebastiaan Heijmann
 */
public class LegWidget extends ConnectionWidget implements BasicWidget {

    private ContainerNode container;

    public LegWidget(Scene scene) {
        super(scene);
        setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        setEndPointShape(PointShape.SQUARE_FILLED_BIG);
    }

    @Override
    public void addActions(ChainGraphScene scene) {
        getActions().addAction(scene.createObjectHoverAction());
        getActions().addAction(scene.createSelectAction());
//        getActions().addAction(scene.reconnectAction);
        getActions().addAction(ActionFactory.createPopupMenuAction(new LegWidget.WidgetPopupMenu()));
    }

    @Override
    public boolean drop(ChainGraphScene scene, ChainBuilder builder, Widget widget, Point point) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setContainer(ContainerNode container) {
        this.container = container;
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
                    scene.removeNodeWithEdges(container);
                }
            });
            return popup;
        }
    }
}
