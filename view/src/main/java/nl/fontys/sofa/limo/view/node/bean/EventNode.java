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
import nl.fontys.sofa.limo.domain.component.Icon;
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

/**
 * View representation of an event that is displayed in the list.
 *
 * @author Sven Mäurer
 */
public class EventNode extends AbstractBeanNode<Event> {

    public EventNode(Event event) throws IntrospectionException {
        super(event, Event.class);
        this.bean = event;
    }

    @Override
    public Action[] getActions(boolean context) {
        ArrayList<Action> actionList = new ArrayList<>();
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("EDIT")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                editEvent();
            }

        });
        actionList.add(new AbstractAction(LIMOResourceBundle.getString("DELETE")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEvent();
            }

        });
        return actionList.toArray(new Action[actionList.size()]);
    }

    /**
     * Open an event wizard with the event for editing.
     */
    private void editEvent() {
        EventWizardAction wiz = new EventWizardAction();
        wiz.setEvent(bean);
        wiz.actionPerformed(null);
        createProperties(getBean(), null);
        setSheet(getSheet());
    }

    /**
     * Delete the event form database.
     */
    private void deleteEvent() {
        int reply = JOptionPane.showConfirmDialog(null, LIMOResourceBundle.getString("DELETE_QUESTION", bean.getName()), LIMOResourceBundle.getString("ARE_YOU_SURE"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (reply == JOptionPane.YES_OPTION) {
            EventService service = Lookup.getDefault().lookup(EventService.class);
            service.delete(bean);
        }
    }

    @Override
    protected void createProperties(Event bean, BeanInfo info) {
        Sheet sets = getSheet();
        Sheet.Set set = super.getNameDescriptionPropertySheet();
        try {
            StupidProperty distributionProp = new StupidProperty(getBean(), Distribution.class, "probability");
            distributionProp.addPropertyChangeListener(getListener());
            distributionProp.setDisplayName(LIMOResourceBundle.getString("PROBABILITY"));
            distributionProp.setShortDescription(LIMOResourceBundle.getString("PROBABILITY_DESC"));
            distributionProp.setValue("canEditAsText", false);

            StupidProperty eventProp = new StupidProperty(getBean(), List.class, "events");
            eventProp.addPropertyChangeListener(getListener());
            eventProp.setPropertyEditorClass(EventPropertyEditor.class);
            eventProp.setDisplayName(LIMOResourceBundle.getString("EVENTS"));
            eventProp.setShortDescription(LIMOResourceBundle.getString("EVENTS_OF", LIMOResourceBundle.getString("EVENT")));
            eventProp.setValue("canEditAsText", false);

            StupidProperty procedureProp = new StupidProperty(getBean(), List.class, "procedures");
            procedureProp.addPropertyChangeListener(getListener());
            procedureProp.setPropertyEditorClass(ProcedurePropertyEditor.class);
            procedureProp.setDisplayName(LIMOResourceBundle.getString("PROCEDURES"));
            procedureProp.setShortDescription(LIMOResourceBundle.getString("PROCEDURES_OF", LIMOResourceBundle.getString("EVENT")));
            procedureProp.setValue("canEditAsText", false);

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

    @Override
    protected Icon getBeanIcon() {
        throw new UnsupportedOperationException(LIMOResourceBundle.getString("NOT_SUPPORTED"));
    }

    @Override
    public void delete() {
        
        EventService service = Lookup.getDefault().lookup(EventService.class);
        service.delete(bean);
    }

}
