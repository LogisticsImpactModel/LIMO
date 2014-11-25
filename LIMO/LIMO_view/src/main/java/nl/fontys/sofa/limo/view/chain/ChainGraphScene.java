package nl.fontys.sofa.limo.view.chain;

import nl.fontys.sofa.limo.view.node.ContainerNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * Abstract class which defines additional methods on the GraphScene.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class ChainGraphScene extends GraphScene<ContainerNode, ContainerNode> {

    public abstract LayerWidget getMainLayer();

    public abstract LayerWidget getConnectionLayer();

    public abstract ChainBuilder getChainBuilder();

    public abstract WidgetAction getSelectAction();

    public abstract WidgetAction getConnectAction();

    public abstract WidgetAction getMoveAlignAction();

    public abstract int getNumberOfHubs();

    public abstract Widget getStartHubWidget();

    public abstract void setStartHubWidget(HubWidget hubwidget);

    public abstract void addHubWidget(HubWidget hubWidget);

}
