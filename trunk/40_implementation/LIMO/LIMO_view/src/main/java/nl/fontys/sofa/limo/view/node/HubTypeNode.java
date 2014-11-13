package nl.fontys.sofa.limo.view.node;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import javax.swing.ImageIcon;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;

/**
 * View representation of HubType.
 *
 * @author Sebastiaan Heijmann
 */
public class HubTypeNode extends AbstractBeanNode<HubType> {

    public HubTypeNode(HubType bean) throws IntrospectionException {
        super(bean, HubType.class);
    }

    @Override
    public boolean canDestroy() {
        return true;
    }
    
    @Override
    protected void createProperties(HubType bean, BeanInfo info) {
        //super.createProperties(bean, info);
        
        Sheet sets = getSheet();
        Sheet.Set set = Sheet.createPropertiesSet();
        
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
            iconProp.setDisplayName("Icon");
            iconProp.setShortDescription("The icon that gets displayed with this Leg-Type.");
            iconProp.setValue("valueIcon", new ImageIcon(getBean().getIcon().getImage()));
            iconProp.setValue("canEditAsText", false);
            
            set.put(name);
            set.put(description);
            set.put(iconProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        sets.put(set);
    }
}
