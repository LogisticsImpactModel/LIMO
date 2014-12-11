package nl.fontys.sofa.limo.view.node.root;

import java.awt.Image;
import javax.swing.Action;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.actions.NewAction;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.actions.SystemAction;

/**
 * Abstract class which defines basic implementations for root nodes. Override
 * getActions and getNewTypes methods to define actions associated with this
 * Node.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class AbstractRootNode extends AbstractNode {

    public AbstractRootNode(Children children) {
        super(children);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[]{SystemAction.get(NewAction.class)};
    }

    abstract Class getServiceClass();

    abstract Class getBeanClass();

    @Override
    public Image getIcon(int type) {
        Image icon = IconUtil.getIcon(getBeanClass(), type);
        if (icon == null) {
            return super.getIcon(type);
        }
        return icon;
    }

}
