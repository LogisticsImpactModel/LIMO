package nl.fontys.sofa.limo.view.chain;

import org.netbeans.api.visual.graph.GraphScene;
import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.widget.BasicWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.action.WidgetAction;
import java.util.List;

/**
 * Abstract class which defines additional methods on the GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class ChainGraphScene extends GraphScene<ContainerNode, String> {

    public abstract List<BasicWidget> getWidgets();

    public abstract LayerWidget getMainLayer();

    public abstract LayerWidget getConnectionLayer();

    public abstract WidgetAction getConnectAction();

    public abstract WidgetAction getMoveAlignAction();

    public abstract void repaintScene();

}
