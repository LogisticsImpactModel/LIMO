package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import javax.swing.Action;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * Abstract class which defines basic implementations for root nodes. Override
 * the getActions method to create custom actions in subclasses.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class AbstractRootNode extends AbstractNode{

	public AbstractRootNode(Children children) {
		super(children);
	}

	abstract Class getBeanClass();

	@Override
	public Image getIcon(int type) {
		Image icon = IconUtil.getIcon(getBeanClass(), type);
		if(icon == null){
			return super.getIcon(type);
		}
		return icon;
	}

	@Override
	public Action[] getActions(boolean context){
		Action[] actions = new Action[0];
		return actions;
	}

}
