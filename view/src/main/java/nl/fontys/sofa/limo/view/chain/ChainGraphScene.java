package nl.fontys.sofa.limo.view.chain;

import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import nl.fontys.sofa.limo.view.topcomponent.DynamicExplorerManagerProvider;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegWidget;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * Abstract class which defines additional methods to perform on a
 * {@link org.netbeans.api.visual.graph.GraphScene}.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class ChainGraphScene
        extends GraphScene<AbstractBeanNode, AbstractBeanNode> {

    /**
     * Get the supply chain from the scene.
     *
     * @return the supply chain belonging to this scene.
     */
    public abstract SupplyChain getSupplyChain();

    /**
     * Get the chain builder from the scene.
     *
     * @return the chain builder belonging to the scene.
     */
    public abstract ChainBuilder getChainBuilder();

    /**
     * Get the select action from the scene.
     *
     * @return the select action.
     */
    public abstract WidgetAction getSelectAction();

    /**
     * Get the connect action from the scene.
     *
     * @return the connect action.
     */
    public abstract WidgetAction getConnectAction();

    /**
     * Get the move alignment action from the scene.
     * <p>
     * Alignment happens by snapping to the grid and displaying helper lines on
     * the scene.
     *
     * @return the move align action.
     */
    public abstract WidgetAction getMoveAlignAction();

    /**
     * Get the starting widget from where the chain starts.
     *
     * @return the starting widget
     */
    public abstract Widget getStartWidget();

    /**
     * Set the starting widget from where the chain starts.
     *
     * @param widget the starting widget.
     */
    public abstract void setStartWidget(Widget widget);

    /**
     * Add a hub widget to the scene.
     *
     * @param hubWidget the hub widget to add.
     */
    public abstract void addHubWidget(HubWidget hubWidget);

    /**
     * Connect 2 hubs together by using a leg.
     *
     * @param source the source widget
     * @param leg the connection widget
     * @param target the target widget
     */
    public abstract void connectHubWidgets(HubWidget source, ConnectionWidget leg, HubWidget target);

    /**
     * Remove a hub widget from the scene.
     *
     * @param hubWidget the hub widget to be removed.
     */
    public abstract void removeHubWidget(HubWidget hubWidget);
    
     /**
     * Remove a leg widget from the scene.
     *
     * @param legWidget the leg widget to be removed.
     */
    public abstract void disconnectLegWidget(LegWidget legWidget);

    /**
     * Get the number of hubs currently displayed in scene.
     *
     * @return the number of hubs in the scene.
     */
    public abstract int getNumberOfHubs();

    /**
     * Get the parent of the scene which is a top component.
     *
     * @return the parent of the scene..
     */
    public abstract DynamicExplorerManagerProvider getParent();

}
