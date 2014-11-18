package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;

/**
 * BasicWidget defines methods for Widgets in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public interface BasicWidget {
    
    /**
     * Add actions to this widget.
     * @param scene - the scene where the widget is dropped on.
     */
    void addActions(GraphScene scene);
   
    
}
