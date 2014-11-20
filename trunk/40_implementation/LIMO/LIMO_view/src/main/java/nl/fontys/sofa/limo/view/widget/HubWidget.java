package nl.fontys.sofa.limo.view.widget;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.WidgetableNode;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.openide.nodes.NodeTransfer;

/**
 * Widget which holds a ContainerNode containing a HubNode. This Widget can be
 * used to display a hub in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public class HubWidget extends BasicWidget {

    private ContainerNode container;

    /**
     * Constructor sets up the widget by setting the display name and image.
     *
     * @param scene - the scene to display the Widget on.
     * @param hubNode - the HubNode.
     */
    public HubWidget(Scene scene) {
        super(scene);
    }

    @Override
    public void addActions(ChainGraphScene scene) {
        getActions().addAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenu()));
        getActions().addAction(scene.createSelectAction());
        getActions().addAction(scene.createObjectHoverAction());
//        getActions().addAction(scene.getConnectAction());
        getActions().addAction(scene.getMoveAlignAction());
    }

    @Override
    public boolean drop(ChainGraphScene scene, Widget widget, Point point) {
        this.setPreferredLocation(point);
        scene.getMainLayer().addChild(this);
        scene.repaintScene();
//        Hub hub = container.getBeanNode().getLookup().lookup(Hub.class);
//        scene.addObject(hub, this);
        return true;
    }

    public Hub getHub() {
        return container.getBeanNode().getLookup().lookup(Hub.class);
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
