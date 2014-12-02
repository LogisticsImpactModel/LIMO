package nl.fontys.sofa.limo.view.node;

import java.awt.Point;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * Interface for a node which can be used as a Widget. Implement this interface
 * to make your node be able to supply a widget.
 *
 * @author Sebastiaan Heijmann
 */
public interface WidgetableNode {

    /**
     * Get the widget from this node.
     *
     * @param scene
     * @return
     */
    public Widget getWidget(Scene scene);

    /**
     * Can this widget be dropped?
     *
     * @param widget - the widget where this widget is dropped on.
     * @param point - the point where the widget is dropped on.
     * @return boolean - true if this widget can be dropped.
     */
    public boolean isAcceptable(Widget widget, Point point);

}
