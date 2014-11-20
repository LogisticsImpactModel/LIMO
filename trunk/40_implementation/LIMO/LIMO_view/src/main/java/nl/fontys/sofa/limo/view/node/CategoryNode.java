package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Node;

/**
 * View representation of the components that are displayed in the palette.
 *
 * @author Sebastiaan Heijmann
 */
public class CategoryNode extends AbstractNode {

    public CategoryNode(Node node) throws IntrospectionException {
        super(node.getChildren());
    }

    @Override
    public boolean canDestroy() {
        return false;
    }

}
