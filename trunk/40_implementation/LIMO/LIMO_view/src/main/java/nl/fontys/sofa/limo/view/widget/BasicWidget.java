package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.chain.GraphSceneImpl2;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 * BasicWidget defines methods for Widgets in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class BasicWidget extends IconNodeWidget {

    public BasicWidget(Scene scene) {
        super(scene);
    }

    /**
     * Add actions to this widget.
     *
     * @param scene - the scene where the widget is dropped on.
     */
    public abstract void addActions(ChainGraphScene scene);

    /**
     * Drop this widget.
     *
     * @param scene - the GraphScene
     * @param widget - the widget where this widget is dropped on
     */
    public abstract boolean drop(ChainGraphScene scene, Widget widget, Point point);

    /**
     * Set the container of the widget.
     *
     * @param container - the container node of this widget.
     */
    public abstract void setContainer(ContainerNode container);

}
