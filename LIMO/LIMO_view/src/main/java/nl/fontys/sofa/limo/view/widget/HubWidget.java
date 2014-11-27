package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.factory.EventChildFactory;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.node.EventRootNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.explorer.view.ListView;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 * HubWidget which represents a Hub in the GraphScene. It holds a ContainerNode
 * which contains a HubNode.
 *
 * @author Sebastiaan Heijmann
 */
public class HubWidget extends IconNodeWidget implements BasicWidget {

    private ContainerNode container;

    /**
     * Constructor sets up the widget by setting the display name and image.
     *
     * @param scene - the scene to display the Widget on.
     */
    public HubWidget(Scene scene, Hub hub) {
        super(scene);
        setImage(hub.getIcon().getImage());
        setLabel(hub.getName());
//        EventsWidget ew = createEventsWidget();
//        addChild(ew);
    }

    @Override
    public void addActions(ChainGraphScene scene) {
        getActions().addAction(scene.getSelectAction());
        getActions().addAction(scene.createObjectHoverAction());
        getActions().addAction(scene.getConnectAction());
        getActions().addAction(scene.getMoveAlignAction());
        getActions().addAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenu()));
    }

    @Override
    public boolean drop(ChainGraphScene scene, Widget widget, Point point) {
        this.setPreferredLocation(point);
        scene.addHubWidget(this);
        return true;
    }

    public Hub getHub() {
        return container.getBeanNode().getLookup().lookup(Hub.class);
    }

    @Override
    public void setContainer(ContainerNode container) {
        this.container = container;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        setImage(getHub().getIcon().getImage());
        setLabel(getHub().getName());
        repaint();
    }

    private EventsWidget createEventsWidget() {
        ListView lv = new ListView();
        Node rootNode;
        Children children = Children.create(new EventChildFactory(getHub().getEvents()), true);
        rootNode = new EventRootNode(children);
        rootNode.setDisplayName("Event");
        EventsWidget ew = new EventsWidget(getScene(), lv);
        ew.getExplorerManager().setRootContext(rootNode);
        return ew;
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
                    scene.removeNodeWithEdges(container);
                }
            });
            return popup;
        }
    }

}
