/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.node;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * View representation of a Leg.
 *
 * @author Sebastiaan Heijmann
 */
public class LegNode extends AbstractBeanNode {

    public LegNode(BaseEntity bean) throws IntrospectionException {
        this(bean, Leg.class);
    }

    public LegNode(BaseEntity bean, Class entityClass) throws IntrospectionException {
        super(bean, entityClass);
    }

    @Override
    public boolean canDestroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException("Copying not supported for leg.");
    }

    @Override
    Class getServiceClass() {
        throw new UnsupportedOperationException("Service not available.");
    }

}
