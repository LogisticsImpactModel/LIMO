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
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/**
 *
 * @author lol
 */
public class ScheduleLegNode extends LegNode {

    public ScheduleLegNode(ScheduledLeg bean) throws IntrospectionException {
        this(bean, ScheduleLegNode.class);
    }

    public ScheduleLegNode(ScheduledLeg bean, Class entityClass) throws IntrospectionException {
        super(bean, entityClass);
    }
              
    @Override
    protected void createProperties(Leg bean, BeanInfo info) {
        Sheet sets = this.getSheet();
        super.createProperties(bean, info); //To change body of generated methods, choose Tools | Templates.
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("ScheduleLeg properties");      
        
        try {
//            scheduleProp = new StupidProperty(getBean(), List.class, "alternative");
//            scheduleProp.addPropertyChangeListener(getListener());
//            scheduleProp.setPropertyEditorClass(EventPropertyEditor.class);
//            scheduleProp.setDisplayName(LIMOResourceBundle.getString("ALTERNATIVE"));
//            scheduleProp.setShortDescription(LIMOResourceBundle.getString("ALTERNATIVE_OF", LIMOResourceBundle.getString("HUB")));
//            scheduleProp.setValue("canEditAsText", false);
//            set.put(scheduleProp);
            
             StupidProperty expectedTime = new StupidProperty(getBean(), Long.class, "expectedTime");
             expectedTime.addPropertyChangeListener(getListener());
             expectedTime.setDisplayName(LIMOResourceBundle.getString("EXPECTED_TIME"));
             expectedTime.setShortDescription(LIMOResourceBundle.getString("EXPECTED_TIME_OF", LIMOResourceBundle.getString("EXPECTED_TIME")));
            
             StupidProperty delay = new StupidProperty(getBean(), Long.class, "delay");
             delay.addPropertyChangeListener(getListener());
             delay.setDisplayName(LIMOResourceBundle.getString("DELAY"));
             delay.setShortDescription(LIMOResourceBundle.getString("DELAY_OF", LIMOResourceBundle.getString("DELAY")));
             
             StupidProperty waitingTimeLimit = new StupidProperty(getBean(), Long.class, "waitingTimeLimit");
             waitingTimeLimit.addPropertyChangeListener(getListener());
             waitingTimeLimit.setDisplayName(LIMOResourceBundle.getString("WAITING_TIME_LIMIT"));
             waitingTimeLimit.setShortDescription(LIMOResourceBundle.getString("WAITING_TIME_LIMIT_OF", LIMOResourceBundle.getString("WAITING_TIME_LIMIT")));
             
             StupidProperty acceptanceTimes = new StupidProperty(getBean(), List.class, "acceptanceTimes");
             acceptanceTimes.addPropertyChangeListener(getListener());
             acceptanceTimes.setDisplayName(LIMOResourceBundle.getString("ACCEPTANCE_TIMES"));
             acceptanceTimes.setShortDescription(LIMOResourceBundle.getString("ACCEPTANCE_TIMES_OF", LIMOResourceBundle.getString("ACCEPTANCE_TIMES")));

             set.put(expectedTime);
             set.put(delay);
             set.put(waitingTimeLimit);
             set.put(acceptanceTimes);
             
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }

        sets.put(set);
    }

}
