package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.custom.pane.GraphSceneImpl;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 * Widget which holds a ContainerNode containing a HubNode. This Widget can be
 * used to display a hub in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public class HubWidget extends IconNodeWidget{
	private ContainerNode container;

	/**
	 * Constructor sets up the widget by setting the display name and image.
	 * @param scene - the scene to display the Widget on.
	 * @param container - the container for the HubNode.
	 */
	public HubWidget(Scene scene, ContainerNode container) {
		super(scene);
		setImage(container.getImage());
		setLabel(container.getDisplayName());
		this.container = container;
		getActions().addAction(ActionFactory.createPopupMenuAction(new WidgetPopupMenu()));
	}

	private class WidgetPopupMenu implements PopupMenuProvider{
		@Override
		public JPopupMenu getPopupMenu(Widget widget, Point localLocation) {
			JPopupMenu popup =  new JPopupMenu();
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

	public Hub getHub(){
		return container.getBeanNode().getLookup().lookup(Hub.class);
	}
}
