package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.beans.BeanInfo;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

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
		super(Children.LEAF);
		this.node = node;
	}

	/**
	 * Get the image from the underlying Node.
	 * @return image - image from the underlying Node.
	 */
	public Image getImage() {
		return node.getIcon(BeanInfo.ICON_COLOR_32x32);
	}

	/**
	 * Get the display name of the underlying node.
	 * @return name - the display name of the Node.
	 */
	@Override
	public String getDisplayName(){
		return node.getDisplayName();
	}

	public AbstractBeanNode getBeanNode(){
		return node;
	}

}
