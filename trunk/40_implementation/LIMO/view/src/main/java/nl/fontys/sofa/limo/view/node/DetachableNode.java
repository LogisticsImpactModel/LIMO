package nl.fontys.sofa.limo.view.node;

import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;

/**
 * Interface which defines methods to detach a BeanNode from the data model.
 * <p>
 * The bean node is detached from the data model inside the graph scene to allow
 * for quick changes that are not persisted. This also allows making "quick and
 * dirty" changes when visiting a customer while still keeping the original data
 * intact.
 *
 * @author Sebastiaan Heijmann
 */
public interface DetachableNode {

    /**
     * Get a detached copy of a node. The bean of this copy is not mapped to a
     * database instance is it sets the id of the bean to null.
     * <p>
     * This is necessary if you want to use this node inside a graph scene
     * because persisting changes inside the graph scene should not be possible
     * to allow changes values quickly without affecting the original master
     * data.
     *
     * @return AbstractBeanNode - the detached node.
     */
    AbstractBeanNode getDetachedNodeCopy();

}
