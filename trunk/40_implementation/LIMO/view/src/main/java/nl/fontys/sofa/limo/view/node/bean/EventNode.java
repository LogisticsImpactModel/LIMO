package nl.fontys.sofa.limo.view.node.bean;

import java.awt.event.ActionEvent;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.view.node.property.StupidProperty;
import nl.fontys.sofa.limo.view.node.property.editor.EventPropertyEditor;
import nl.fontys.sofa.limo.view.node.property.editor.ProcedurePropertyEditor;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.event.EventWizardAction;
import org.openide.ErrorManager;
import org.openide.nodes.Sheet;
import org.openide.util.Lookup;

public class EventNode extends AbstractBeanNode<Event> {
    
    public EventNode(Event event) throws IntrospectionException {
        super(event, Event.class);
        this.bean = event;
    }
    
    @Override
    public boolean canDestroy() {
        return true;
        
    }
    
    @Override
    public Action[] getActions(boolean context) {
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("EDIT")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventWizardAction wiz = new EventWizardAction();
                wiz.setEvent(bean);
                wiz.actionPerformed(e);
                createProperties(getBean(), null);
                setSheet(getSheet());
            }
        });
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("DELETE")) {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, LIMOResourceBundle.getString("DELETE_QUESTION", bean.getName()), LIMOResourceBundle.getString("ARE_YOU_SURE"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (reply == JOptionPane.YES_OPTION) {
                    EventService service = Lookup.getDefault().lookup(EventService.class);
                    service.delete(bean);
                }
            }
        });
        return actionList.toArray(new Action[actionList.size()]);
    }
    
    @Override
    protected void createProperties(Event bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = Sheet.createPropertiesSet();
        set.setName("properties");
        set.setDisplayName(LIMOResourceBundle.getString("PROPERTIES"));
        
        try {
            StupidProperty name = new StupidProperty<>(getBean(), String.class, "name");
            name.addPropertyChangeListener(getListener());
            name.setDisplayName(LIMOResourceBundle.getString("NAME"));
            name.setShortDescription(LIMOResourceBundle.getString("NAME_OF", "Procedure Category"));
            
            StupidProperty description = new StupidProperty<>(getBean(), String.class, "description");
            description.addPropertyChangeListener(getListener());
            description.setDisplayName(LIMOResourceBundle.getString("DESCRIPTION"));
            description.setShortDescription(LIMOResourceBundle.getString("DESCRIPTION_OF", "Procedure Category"));
            
            StupidProperty distributionProp = new StupidProperty(getBean(), Distribution.class, "probability");
            distributionProp.addPropertyChangeListener(getListener());
            distributionProp.setDisplayName(LIMOResourceBundle.getString("Probability"));
            distributionProp.setShortDescription(LIMOResourceBundle.getString("PROBABILITY_DESC"));
            distributionProp.setValue("canEditAsText", false);
            
            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class);
            eventProp.setDisplayName(LIMOResourceBundle.getString("EVENTS"));
            eventProp.setShortDescription(LIMOResourceBundle.getString("EVENTS_OF", "Event"));
            eventProp.setValue("canEditAsText", false);
            
            StupidProperty procedureProp = new StupidProperty(getBean(), List.class, "procedures");
            procedureProp.addPropertyChangeListener(getListener());
            procedureProp.setPropertyEditorClass(ProcedurePropertyEditor.class);
            procedureProp.setDisplayName(LIMOResourceBundle.getString("PROCEDURES"));
            procedureProp.setShortDescription(LIMOResourceBundle.getString("PROCEDURES_OF", "Hub"));
            procedureProp.setValue("canEditAsText", false);
            
            set.put(name);
            set.put(description);
            set.put(distributionProp);
            set.put(procedureProp);
            set.put(eventProp);
        } catch (NoSuchMethodException ex) {
            ErrorManager.getDefault();
        }
        sets.put(set);
    }
    
    @Override
    public AbstractBeanNode getDetachedNodeCopy() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("COPY_NOT_SUPPORTED"));
    }
    
    @Override
    Class getServiceClass() {
        return EventService.class;
    }
    
}
