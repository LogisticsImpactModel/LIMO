package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import nl.fontys.sofa.limo.view.chain.ChainBuilder;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.widget.Widget;

/**
 * BasicWidget defines methods for Widgets in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public interface BasicWidget {

    /**
     * Add actions to this widget.
     *
     * @param scene - the scene where the widget is dropped on.
     */
    void addActions(ChainGraphScene scene);

    /**
     * Drop this widget.
     *
     * @param scene - the GraphScene
     * @param widget - the widget where this widget is dropped on
     */
    boolean drop(ChainGraphScene scene, ChainBuilder builder, Widget widget, Point point);

    /**
     * Set the container of the widget.
     *
     * @param container - the container node of this widget.
     */
    void setContainer(ContainerNode container);

}
