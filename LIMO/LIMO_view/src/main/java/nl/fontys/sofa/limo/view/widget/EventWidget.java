package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * Widget which holds a ContainerNode containing an EventNode. This Widget can
 * be used to display an Event in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public class EventWidget extends LabelWidget implements BasicWidget {

    private ContainerNode container;

    /**
     * Constructor sets up the widget by setting the display name and image.
     *
     * @param scene - the scene to display the Widget on.
     * @param container - the container for the EventNode.
     */
    public EventWidget(Scene scene) {
        super(scene);
    }

    @Override
    public void addActions(ChainGraphScene scene) {
        getActions().addAction(ActionFactory.createPopupMenuAction(new EventWidget.WidgetPopupMenu()));
        getActions().addAction(scene.createSelectAction());
        getActions().addAction(scene.createObjectHoverAction());
    }

    @Override
    public boolean drop(ChainGraphScene scene, Widget widget, Point point) {
//        List<Widget> widgets = scene.getWidgets();
//        for (Widget w : widgets) {
//            Point localPoint = w.convertSceneToLocal(point);
//            if (w.isHitAt(localPoint)) {
//                w.addChild(this);
////                scene.repaintScene();
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public void setContainer(ContainerNode container) {
        this.container = container;
    }

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
                    scene.removeNodeWithEdges(container);
                }
            });
            return popup;
        }
    }

}
