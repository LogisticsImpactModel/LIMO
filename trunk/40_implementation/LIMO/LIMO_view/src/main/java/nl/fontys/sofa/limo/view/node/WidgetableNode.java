package nl.fontys.sofa.limo.view.node;

import java.awt.Point;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.graph.GraphScene;

/**
 * Interface for a node which can be used as a Widget. Implement this interface
 * to make your node be able to supply a widget.
 *
 * @author Sebastiaan Heijmann
 */
public interface WidgetableNode {
    
    /**
     * Get the widget from this node.
     * @param scene
     * @return 
     */
    public Widget getWidget(GraphScene scene);
    
}
