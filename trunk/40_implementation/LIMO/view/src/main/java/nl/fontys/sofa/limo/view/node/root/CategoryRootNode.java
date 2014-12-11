package nl.fontys.sofa.limo.view.node.root;

import javax.swing.Action;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author Sebastiaan Heijmann
 */
public class CategoryRootNode extends AbstractNode {

    public CategoryRootNode(Children children) {
        super(children);
    }

    @Override
    public Action[] getActions(boolean context) {
        return new Action[0];
    }

}
