package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.chain.GraphSceneImpl;
import nl.fontys.sofa.limo.view.node.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 * Widget which holds a ContainerNode containing a HubNode. This Widget can be
 * used to display a hub in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public class HubWidget extends IconNodeWidget implements BasicWidget {

    private ContainerNode container;

    /**
     * Constructor sets up the widget by setting the display name and image.
     *
     * @param scene - the scene to display the Widget on.
     * @param hubNode - the HubNode.
     */
    public HubWidget(Scene scene, AbstractBeanNode hubNode) {
        super(scene);
        this.container = new ContainerNode(hubNode);
        setImage(container.getImage());
        setLabel(container.getDisplayName());
        getActions().addAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenu()));
    }

    @Override
    public void addActions(ChainGraphScene scene) {
        getActions().addAction(scene.createSelectAction());
        getActions().addAction(scene.createObjectHoverAction());
        getActions().addAction(scene.getConnectAction());
        getActions().addAction(scene.getMoveAlignAction());
    }

    @Override
    public boolean isAcceptable(Widget widget, Point point) {
        if(widget instanceof ChainGraphScene){
            return true;
        }
        return false;
    }

    @Override
    public void drop(Widget widget, Point point) {
        this.setPreferredLocation(point);
        widget.addChild(this);
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
                    GraphSceneImpl scene = (GraphSceneImpl) getScene();
                    scene.removeNodeWithEdges(container);
                }
            });
            return popup;
        }
    }

    public Hub getHub() {
        return container.getBeanNode().getLookup().lookup(Hub.class);
    }
}
