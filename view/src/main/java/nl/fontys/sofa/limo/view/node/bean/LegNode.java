package nl.fontys.sofa.limo.view.node.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ProcedurePropertyEditor;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;

/**
 * View representation of a Leg. This class is used to display a NormalLeg and
 * is used by ScheduledLegNode and MultiModeLegNode.
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
    
    protected PropertyChangeListener getListener() {
        return new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
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
        return null;
    }
    
    @Override
    protected Icon getBeanIcon() {
        return getBean().getIcon();
    }
    
    public void refresh() {
        createProperties(bean, null);
    }
    
    @Override
    protected void createProperties(Leg bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = super.getBaseEntityPropertySheet();
        
        try {
            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class);
            eventProp.setDisplayName(LIMOResourceBundle.getString("EVENTS"));
            eventProp.setShortDescription(LIMOResourceBundle.getString("EVENTS_OF", LIMOResourceBundle.getString("HUB")));
            eventProp.setValue("canEditAsText", false);
            
            StupidProperty procedureProp = new StupidProperty(getBean(), List.class, "procedures");
            procedureProp.addPropertyChangeListener(getListener());
            procedureProp.setPropertyEditorClass(ProcedurePropertyEditor.class);
            procedureProp.setDisplayName(LIMOResourceBundle.getString("PROCEDURES"));
            procedureProp.setShortDescription(LIMOResourceBundle.getString("PROCEDURES_OF", LIMOResourceBundle.getString("HUB")));
            procedureProp.setValue("canEditAsText", false);
            set.put(procedureProp);
            set.put(eventProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        sets.put(set);
    }

    @Override
    public void delete() {
        //TODO
    }
    
}
