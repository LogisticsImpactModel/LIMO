package nl.fontys.sofa.limo.view.node.bean;

import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

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
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("NOT_SUPPORTED"));
    }

    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("COPY_NOT_SUPPORTED"));
    }

    @Override
    Class getServiceClass() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("SERVICE_UNAVAILABLE"));
    }

    @Override
    protected Icon getBeanIcon() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("NOT_SUPPORTED"));
    }

}
