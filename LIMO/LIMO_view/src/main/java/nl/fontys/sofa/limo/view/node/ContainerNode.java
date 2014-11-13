package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.beans.BeanInfo;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;

/**
 * Container for nodes used in a GraphScene. <p> Wrap a Node in this container
 * when adding nodes to widgets to avoid an assertion error when adding the same
 * widget twice in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public class ContainerNode extends AbstractNode{

	private final AbstractBeanNode node;

	public ContainerNode(AbstractBeanNode node) {
		super(node.getChildren());
		this.node = node;
	}

	/**
	 * Get the image from the underlying Node.
	 * @return image - image from the underlying Node.
	 */
	public Image getImage() {
		if(node.getEntityClass() == Hub.class){
			return node.getLookup().lookup(Hub.class).getIcon().getImage();
		}else if(node.getEntityClass() == Leg.class){
			return node.getLookup().lookup(Leg.class).getIcon().getImage();
		}else{
			return IconUtil.getIcon(node.getEntityClass(), BeanInfo.ICON_COLOR_32x32);
		}
	}

	/**
	 * Get the display name of the underlying node.
	 * @return name - the display name of the Node.
	 */
	@Override
	public String getDisplayName(){
		return node.getDisplayName();
	}

	/**
	 * Get the contained BeanNode.
	 * @return AbstractBeanNode - the contained Node.
	 */
	public AbstractBeanNode getBeanNode(){
		return node;
	}

}
