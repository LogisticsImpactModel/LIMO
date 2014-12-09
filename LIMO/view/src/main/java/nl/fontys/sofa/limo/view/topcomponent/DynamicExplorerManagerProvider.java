package nl.fontys.sofa.limo.view.topcomponent;

import nl.fontys.sofa.limo.view.node.AbstractBeanNode;
import org.openide.explorer.ExplorerManager;

/**
 * DynamicRootContext defines methods for topcomponents to dynamically update
 * the root context of the ExplorerManager.
 *
 * @author Sebastiaan Heijmann
 */
public interface DynamicExplorerManagerProvider extends ExplorerManager.Provider {

    /**
     * Set the root context of the explorer manager.
     *
     * @param node the node to set the root context to.
     */
    void setRootContext(AbstractBeanNode node);

    @Override
    public ExplorerManager getExplorerManager();

}
