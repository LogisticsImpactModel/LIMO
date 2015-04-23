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
import nl.fontys.sofa.limo.view.node.property.editor.AcceptanceTimesPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.LegPropertyEditor;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/**
 * ScheduledLegNode is a scpecific version from a LegNode. It extends the
 * Properties of the LegNode. It adds all ScheduleLeg specific properties to the
 * Property sheet.
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
        super.createProperties(bean, info);
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName(LIMOResourceBundle.getString("SCHEDULED_LEG_PROPERTIES"));
        set.setDisplayName(LIMOResourceBundle.getString("SCHEDULED_LEG_PROPERTIES"));
        
        try {
            
            StupidProperty expectedTime = new StupidProperty(getBean(), long.class, "expectedTime");
            expectedTime.addPropertyChangeListener(getListener());
            expectedTime.setDisplayName(LIMOResourceBundle.getString("EXPECTED_TIME"));
            expectedTime.setShortDescription(LIMOResourceBundle.getString("EXPECTED_TIME_OF", LIMOResourceBundle.getString("EXPECTED_TIME")));
            
            StupidProperty waitingTimeLimit = new StupidProperty(getBean(), long.class, "waitingTimeLimit");
            waitingTimeLimit.addPropertyChangeListener(getListener());
            waitingTimeLimit.setDisplayName(LIMOResourceBundle.getString("WAITING_TIME_LIMIT"));
            waitingTimeLimit.setShortDescription(LIMOResourceBundle.getString("WAITING_TIME_LIMIT_OF", LIMOResourceBundle.getString("WAITING_TIME_LIMIT")));
            
            StupidProperty acceptanceTimes = new StupidProperty(getBean(), List.class, "acceptanceTimes");
            acceptanceTimes.addPropertyChangeListener(getListener());
            acceptanceTimes.setDisplayName(LIMOResourceBundle.getString("ACCEPTANCE_TIMES"));
            acceptanceTimes.setShortDescription(LIMOResourceBundle.getString("ACCEPTANCE_TIMES_OF", LIMOResourceBundle.getString("ACCEPTANCE_TIMES")));
            acceptanceTimes.setPropertyEditorClass(AcceptanceTimesPropertyEditor.class);
            acceptanceTimes.setValue("canEditAsText", false);
            
            StupidProperty alternativeLeg = new StupidProperty(getBean(), Leg.class, "alternative");
            alternativeLeg.addPropertyChangeListener(getListener());
            alternativeLeg.setDisplayName(LIMOResourceBundle.getString("ALTERNATIVE"));
            alternativeLeg.setShortDescription(LIMOResourceBundle.getString("ALTERNATIVE_OF", LIMOResourceBundle.getString("ALTERNATIVE")));
            alternativeLeg.setPropertyEditorClass(LegPropertyEditor.class);
            
            alternativeLeg.setValue("canEditAsText", false);
            
            set.put(expectedTime);
            set.put(waitingTimeLimit);
            set.put(acceptanceTimes);
            set.put(alternativeLeg);
            
        } catch (NoSuchMethodException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        sets.put(set);
    }
    
}
