package nl.fontys.sofa.limo.view.node;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.List;
import javax.swing.ImageIcon;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.IconPropertyEditor;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;

/**
 * View representation of the LegType class.
 *
 * @author Sebastiaan Heijmann
 */
public class LegTypeNode extends AbstractBeanNode<LegType> {

    public LegTypeNode(LegType bean) throws IntrospectionException {
        super(bean, LegType.class);
    }

    @Override
    public boolean canDestroy() {
        return true;
    }
    
    @Override
    protected void createProperties(LegType bean, BeanInfo info) {
        super.createProperties(bean, info);
        
        Sheet sets = getSheet();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("properties");
        set.setDisplayName("Properties");
        
        try {
            StupidProperty name = new StupidProperty<>(getBean(), String.class, "name");
            name.addPropertyChangeListener(getListener());
            name.setDisplayName("Name");
            name.setShortDescription("The name of the procedure category.");
            
            StupidProperty description = new StupidProperty<>(getBean(), String.class, "description");
            description.addPropertyChangeListener(getListener());
            description.setDisplayName("Description");
            description.setShortDescription("An optional short description of the procedure category.");
            
            StupidProperty iconProp = new StupidProperty(getBean(), Icon.class, "icon");
            iconProp.addPropertyChangeListener(getListener());
            iconProp.setPropertyEditorClass(IconPropertyEditor.LegIconPropertyEditor.class);
            iconProp.setDisplayName("Icon");
            iconProp.setShortDescription("The icon that gets displayed with this Leg-Type.");
            iconProp.setValue("valueIcon", new ImageIcon(getBean().getIcon().getImage()));
            iconProp.setValue("canEditAsText", false);
            
            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class);
            eventProp.setDisplayName("Event");
            eventProp.setShortDescription("All Events stored with this Hub.");
            eventProp.setValue("canEditAsText", false);
             
            set.put(name);
            set.put(description);
            set.put(iconProp);
            set.put(eventProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        sets.put(set);
    }
}
