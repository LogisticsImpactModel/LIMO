package nl.fontys.sofa.limo.view.node;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * AbstractBeanNode class which defines basic Node actions and creates a lookup
 * for the underlying bean.
 *
 * @author Sebastiaan Heijmann
 */
public abstract class AbstractBeanNode<T extends BaseEntity> extends BeanNode<T>
        implements DetachableNode {

    private Class entityClass;
    private PropertyChangeListener listener;
    InstanceContent ic;

    /**
     * Abstract class which defines basic implementations for nodes and binds
     * the datamodel. The datamodel is available through the AbstractBeanNode's
     * lookup.
     * <p>
     * Override getActions and getNewTypes methods to define actions associated
     * with this Node.
     *
     * @param bean the underlying datamodel
     * @throws IntrospectionException
     */
    public AbstractBeanNode(T bean, Class entityClass) throws IntrospectionException {
        this(bean, new InstanceContent());
        this.entityClass = entityClass;
    }

    private AbstractBeanNode(T bean, InstanceContent ic) throws IntrospectionException {
        super(bean, Children.LEAF, new AbstractLookup(ic));
        this.ic = ic;
        ic.add(bean);
        String description = bean.getDescription();
        String name = bean.getName();
        setShortDescription(description);
        setDisplayName(name);
    }

    @Override
    public Image getIcon(int type) {
        Image icon = IconUtil.getIcon(entityClass, type);
        if (icon == null) {
            return super.getIcon(type);
        }
        return icon;
    }

    @Override
    public abstract boolean canDestroy();

    @Override
    public void destroy() throws IOException {
        fireNodeDestroyed();
    }

    protected PropertyChangeListener getListener() {
        if (this.listener == null) {
            this.listener = new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    DAO service = (DAO) Lookup.getDefault().lookup(getServiceClass());
                    service.update(getBean());
//                    ((AbstractRootNode) getParentNode()).getService().update(getBean());
                    firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
                    switch (evt.getPropertyName()) {
                        case "name":
                            setDisplayName((String) evt.getNewValue());
                            break;
                        case "description":
                            setShortDescription((String) evt.getNewValue());
                            break;
                        case "icon":
                            createProperties(getBean(), null);
                            setSheet(getSheet());
                            break;
                    }
                }
            };
        }

        return this.listener;
    }

    /**
     * Get the entity class name.
     *
     * @return Class - the class from this entity.
     */
    public Class getEntityClass() {
        return entityClass;
    }

    /**
     * Get the service class of this bean.
     *
     * @return Class - the service class.
     */
    abstract Class getServiceClass();
}
