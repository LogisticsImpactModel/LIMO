package nl.fontys.sofa.limo.view.chain;

import java.util.List;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * Abstract class which defines additional methods on the GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class ChainGraphScene extends GraphScene<ContainerNode, String> {

    public abstract List<Widget> getWidgets();

    public abstract LayerWidget getMainLayer();

    public abstract LayerWidget getConnectionLayer();

    public abstract WidgetAction getConnectAction();

    public abstract WidgetAction getMoveAlignAction();

    public abstract void repaintScene();

}
