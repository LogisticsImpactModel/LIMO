package nl.fontys.sofa.limo.view.node.root;

import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 * RootNode for the categories. The categories are used to display in the
 * palette.
 *
 * @author Sebastiaan Heijmann
 */
public class CategoryRootNode extends AbstractNode {

    /**
     * Constructor creates a new CategoryRootNode.
     *
     * @param children the children of this node.
     */
    public CategoryRootNode(Children children) {
        super(children);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[0];
    }

}
