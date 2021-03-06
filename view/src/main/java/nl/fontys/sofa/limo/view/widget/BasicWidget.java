package nl.fontys.sofa.limo.view.widget;

import java.awt.Point;
import java.beans.PropertyChangeListener;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.Deletable;
import org.netbeans.api.visual.widget.Widget;

/**
 * BasicWidget defines methods for Widgets in a GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public interface BasicWidget extends PropertyChangeListener, Deletable{

    /**
     * Add actions to this widget.
     *
     * @param scene - the scene where the widget is dropped on.
     */
    void addActions(ChainGraphScene scene);

    /**
     * Drop action for this widget. Every widget should implement this method to
     * define what happens with the widget when it is dropped on the scene.
     *
     * @param scene - the GraphScene where the widget is dropped on.
     * @param widget - the widget where this widget is dropped on.
     * @param point - the location as a point where the widget is dropped.
     */
    boolean drop(ChainGraphScene scene, Widget widget, Point point);

}
