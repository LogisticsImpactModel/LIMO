package nl.fontys.sofa.limo.view.custom.pane;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.view.node.NodeHolder;
import org.netbeans.api.visual.action.AcceptProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.ImageUtilities;

/**
 * The GraphScene where the chain is build on.
 *
 * @author Sebastiaan Heijmann
 */
public class ChainScene extends GraphScene<NodeHolder, String> {

	private LayerWidget mainLayer;

	public ChainScene() {
		mainLayer = new LayerWidget(this);
		addChild(mainLayer);

		getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {

			@Override
			public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
				Image dragImage = getImageFromTransferable(transferable);
				JComponent view = getView();
				Graphics2D g2 = (Graphics2D) view.getGraphics();
				Rectangle visRect = view.getVisibleRect();
				view.paintImmediately(visRect.x, visRect.y, visRect.width, visRect.height);
				g2.drawImage(dragImage,
						AffineTransform.getTranslateInstance(point.getLocation().getX(),
								point.getLocation().getY()),
						null);
				return ConnectorState.ACCEPT;
			}

			@Override
			public void accept(Widget widget, Point point, Transferable transferable) {
				Image image = getImageFromTransferable(transferable);
				Widget w = ChainScene.this.addNode(new NodeHolder(image));
				w.setPreferredLocation(widget.convertLocalToScene(point));
			}
		}));
	}

	private Image getImageFromTransferable(Transferable transferable) {
		Object o = null;
		try {
			o = transferable.getTransferData(DataFlavor.imageFlavor);
		} catch (IOException ex) {
		} catch (UnsupportedFlavorException ex) {
		}
		return (Image) o;
	}

	@Override
	protected Widget attachNodeWidget(NodeHolder n) {
		IconNodeWidget widget = new IconNodeWidget(this);
		widget.setImage(n.getImage());
		widget.setLabel(Long.toString(n.hashCode()));
		widget.getActions().addAction(ActionFactory.createMoveAction());
		mainLayer.addChild(widget);
		return widget;
	}

	@Override
	protected Widget attachEdgeWidget(String e) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void attachEdgeSourceAnchor(String e, NodeHolder n, NodeHolder n1) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void attachEdgeTargetAnchor(String e, NodeHolder n, NodeHolder n1) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
