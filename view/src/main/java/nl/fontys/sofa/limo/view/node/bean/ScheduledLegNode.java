/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.node.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.LegPropertyEditor;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/**
 *
 * @author Christina Zenzes
 */
public class ScheduledLegNode extends LegNode {

    public ScheduledLegNode(ScheduledLeg bean) throws IntrospectionException {
        this(bean, ScheduledLeg.class);
    }

    public ScheduledLegNode(ScheduledLeg bean, Class entityClass) throws IntrospectionException {
        super(bean, entityClass);
    }
              
    @Override
    protected void createProperties(Leg bean, BeanInfo info) {
        Sheet sets = this.getSheet();
        super.createProperties(bean, info); //To change body of generated methods, choose Tools | Templates.
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("ScheduleLeg properties"); 
        set.setDisplayName("Schedule Leg properties");
       
        try {
            
             StupidProperty expectedTime = new StupidProperty(getBean(), long.class, "expectedTime");
             expectedTime.addPropertyChangeListener(getListener());
             expectedTime.setDisplayName(LIMOResourceBundle.getString("EXPECTED_TIME"));
             expectedTime.setShortDescription(LIMOResourceBundle.getString("EXPECTED_TIME_OF", LIMOResourceBundle.getString("EXPECTED_TIME")));
            
             StupidProperty delay = new StupidProperty(getBean(), long.class, "delay");
             delay.addPropertyChangeListener(getListener());
             delay.setDisplayName(LIMOResourceBundle.getString("DELAY"));
             delay.setShortDescription(LIMOResourceBundle.getString("DELAY_OF", LIMOResourceBundle.getString("DELAY")));
             
             StupidProperty waitingTimeLimit = new StupidProperty(getBean(), long.class, "waitingTimeLimit");
             waitingTimeLimit.addPropertyChangeListener(getListener());
             waitingTimeLimit.setDisplayName(LIMOResourceBundle.getString("WAITING_TIME_LIMIT"));
             waitingTimeLimit.setShortDescription(LIMOResourceBundle.getString("WAITING_TIME_LIMIT_OF", LIMOResourceBundle.getString("WAITING_TIME_LIMIT")));
             
             StupidProperty acceptanceTimes = new StupidProperty(getBean(), List.class, "acceptanceTimes");
             acceptanceTimes.addPropertyChangeListener(getListener());
             acceptanceTimes.setDisplayName(LIMOResourceBundle.getString("ACCEPTANCE_TIMES"));
             acceptanceTimes.setShortDescription(LIMOResourceBundle.getString("ACCEPTANCE_TIMES_OF", LIMOResourceBundle.getString("ACCEPTANCE_TIMES")));

             StupidProperty alternativeLeg = new StupidProperty(getBean(), Leg.class, "alternative");
             alternativeLeg.addPropertyChangeListener(getListener());
             alternativeLeg.setDisplayName(LIMOResourceBundle.getString("ALTERNATIVE"));
             alternativeLeg.setShortDescription(LIMOResourceBundle.getString("ALTERNATIVE_OF", LIMOResourceBundle.getString("ALTERNATIVE")));
             alternativeLeg.setPropertyEditorClass(LegPropertyEditor.class);
             alternativeLeg.setValue("canEditAsText", false);

             set.put(expectedTime);
             set.put(delay);
             set.put(waitingTimeLimit);
             set.put(acceptanceTimes);
             set.put(alternativeLeg);
             
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }

        sets.put(set);
    }

}
