/*
 *  Created by Mike de Roode
 */
package nl.fontys.sofa.limo.view.node.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.Map;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.MultiModeLegPropertyEditor;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/**
 *
 * @author Mike
 */
public class MultiModeLegNode extends LegNode {

    public MultiModeLegNode(MultiModeLeg bean) throws IntrospectionException {
        super(bean);
    }

    public MultiModeLegNode(MultiModeLeg bean, Class entityClass) throws IntrospectionException {
        super(bean, entityClass);
    }

    @Override
    protected void createProperties(Leg bean, BeanInfo info) {
        Sheet sets = this.getSheet();
        
        Sheet.Set set = Sheet.createPropertiesSet(); //Procedures and Events should not be included
        set.setName("Multi-mode leg properties");
        set.setDisplayName("Multi-mode properties");

        try {
            StupidProperty legs = new StupidProperty(getBean(), Map.class, "legs");
            legs.addPropertyChangeListener(getListener());
            legs.setPropertyEditorClass(MultiModeLegPropertyEditor.class);
            legs.setDisplayName(LIMOResourceBundle.getString("LEGS"));
            legs.setShortDescription("All the legs which are part of the multi-mode leg");
            legs.setValue("canEditAsText", false);

            set.put(legs);

        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }

        sets.put(super.getBaseEntityPropertySheet()); //Name, description, icon
        sets.put(set);
    }

}
