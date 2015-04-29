package nl.fontys.sofa.limo.view.node.bean;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import nl.fontys.sofa.limo.api.dao.DAO;
import nl.fontys.sofa.limo.domain.BaseEntity;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.view.node.DetachableNode;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.IconPropertyEditor;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.ErrorManager;
import org.openide.nodes.BeanNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
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
    protected InstanceContent ic;
    protected T bean;

    /**
     * Abstract class which defines basic implementations for nodes and binds
     * the data model. The data model is available through the
     * AbstractBeanNode's lookup.
     * <p>
     * Override getActions and getNewTypes methods to define actions associated
     * with this Node.
     *
     * @param bean the underlying data model
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
    public void destroy() throws IOException {
        fireNodeDestroyed();
    }

    /**
     * Get the property change listener.
     *
     * @return the property change listener.
     */
    protected PropertyChangeListener getListener() {
        if (this.listener == null) {
            this.listener = new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    
                    DAO service = (DAO) Lookup.getDefault().lookup(getServiceClass());
                    service.update(getBean());
                    firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
                    if (evt.getPropertyName().equals("name")) {
                        setDisplayName((String) evt.getNewValue());
                    } else if (evt.getPropertyName().equals("description")) {
                        setShortDescription((String) evt.getNewValue());
                    } else if (evt.getPropertyName().equals("icon")) {
                        createProperties(getBean(), null);
                        setSheet(getSheet());
                    }
                }
            };
        }

        return this.listener;
    }

    /**
     * Get the propertysheet for name and description.
     *
     * @return the propertysheet set.
     */
    protected Sheet.Set getNameDescriptionPropertySheet() {
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("properties");
        set.setDisplayName("General properties");

        try {
            StupidProperty name = new StupidProperty<>(getBean(), String.class, "name");
            name.addPropertyChangeListener(getListener());
            name.setDisplayName(LIMOResourceBundle.getString("NAME"));
            name.setShortDescription(LIMOResourceBundle.getString("NAME_OF", LIMOResourceBundle.getString("EVENT")));

            StupidProperty description = new StupidProperty<>(getBean(), String.class, "description");
            description.addPropertyChangeListener(getListener());
            description.setDisplayName(LIMOResourceBundle.getString("DESCRIPTION"));
            description.setShortDescription(LIMOResourceBundle.getString("DESCRIPTION_OF", LIMOResourceBundle.getString("EVENT")));

            set.put(name);
            set.put(description);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        return set;
    }

    /**
     * Get the propertysheet for the base entity.
     *
     * @return the propertysheet set.
     */
    protected Sheet.Set getBaseEntityPropertySheet() {
        Sheet.Set set = this.getNameDescriptionPropertySheet();

        try {
            StupidProperty iconProp = new StupidProperty(getBean(), Icon.class, "icon");
            iconProp.addPropertyChangeListener(getListener());
            iconProp.setPropertyEditorClass(IconPropertyEditor.HubIconPropertyEditor.class);
            iconProp.setDisplayName(LIMOResourceBundle.getString("ICON"));
            iconProp.setShortDescription(LIMOResourceBundle.getString("ICON_OF", LIMOResourceBundle.getString("HUB_TYPE")));
            iconProp.setValue("valueIcon", new ImageIcon(getBeanIcon().getImage()));
            iconProp.setValue("canEditAsText", false);

            set.put(iconProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }

        return set;
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

    /**
     * Get the icon for this bean.
     *
     * @return the icon of the bean.
     */
    protected abstract Icon getBeanIcon();

    @Override
    public boolean canDestroy() {
        return false;
    }

    @Override
    public boolean canCut() {
        return false;
    }

    @Override
    public boolean canCopy() {
        return false;
    }

    @Override
    public boolean canRename() {
        return false;
    }

}
