/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.node;

/**
 * Interface which defines methods to detach a BeanNode from the datamodel.
 * <p>
 * The beannode is detached from the datamodel inside the Graphscene to allow
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
     * This is necessary if you want to use this node inside a graphscene
     * because persisting changes inside the graphscene should not be posible to
     * allow changes values quickly without affecting the original masterdata.
     *
     * @return AbstractBeanNode - the detached node.
     */
    AbstractBeanNode getDetachedNodeCopy();

}
