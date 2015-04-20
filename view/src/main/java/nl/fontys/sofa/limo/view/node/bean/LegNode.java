package nl.fontys.sofa.limo.view.node.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Sheet;

/**
 * View representation of a Leg.
 *
 * @author Sebastiaan Heijmann
 */
public class LegNode extends AbstractBeanNode<Leg> {

    /**
     * /**
     * constructor for LegNode, calls the second constructor with the correct
     * class name.
     *
     * @param bean
     * @throws IntrospectionException
     */
    public LegNode(Leg bean) throws IntrospectionException {
        this(bean, Leg.class);
    }

    /**
     * constructor for LegNode, adds the bean to the instancecontent.
     *
     * @param bean the base entity
     * @param entityClass the class name of the entity
     * @throws IntrospectionException
     */
    public LegNode(Leg bean, Class entityClass) throws IntrospectionException {
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
          return getBean().getIcon();
    }
    
    @Override
    protected void createProperties(Leg bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = super.getBaseEntityPropertySheet();

        sets.put(set);
    }
}
