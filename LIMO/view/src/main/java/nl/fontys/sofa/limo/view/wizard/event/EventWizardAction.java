package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.EventService;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;

/**
 * The EventWizardAction is the entry point of the event wizard which is used
 * for creation and editing of events. The wizard includes the choice for new or
 * duplicate, name, description, icon, sub events and procedures.
 *
 * @author Sven Mäurer
 */
@ActionID(
        category = "Event",
        id = "nl.fontys.sofa.limo.view.wizard.event.EventWizardAction"
)
@ActionRegistration(
        displayName = "New Event...",
        iconBase = "icons/gui/add.gif"
)
@ActionReferences({
    @ActionReference(path = "Menu/Master Data/Event", position = 20),
    @ActionReference(path = "Shortcuts", name = "DS-E")
})
public final class EventWizardAction implements ActionListener {

    static final String EVENT = "event";
    static final String EVENT_NAME = "eventName";
    static final String EVENT_DESCRIPTION = "eventDescription";
    static final String EVENT_PROCEDURES = "eventProcedures";
    static final String EVENT_PROBABILITY = "eventProbability";
    static final String EVENT_EVENTS = "eventEvents";

    private Event eventUpdate;
    private boolean isUpdate = false;
    private final EventService service = Lookup.getDefault().lookup(EventService.class);

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!isUpdate) {
            eventUpdate = new Event();
            panels.add(new NewOrDuplicatedEventWizard());
        }
        panels.add(new NameDescriptionProbabilityWizard());
        List<Event> events = service.findAll();
        if (!events.isEmpty() && !(events.size() == 1 && events.contains(eventUpdate))) {
            panels.add(new SubEventsWizard());
        }
        panels.add(new ProceduresWizard());
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            steps[i] = c.getName();
            if (c instanceof JComponent) {
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.putProperty(WizardDescriptor.PROP_IMAGE, ImageUtilities.loadImage("icons/limo_wizard.png", true));
        if (isUpdate) {
            wiz.setTitle(LIMOResourceBundle.getString("EDIT_EVENT"));
            wiz.putProperty(EVENT, eventUpdate);
        } else {
            wiz.setTitle(LIMOResourceBundle.getString("ADD_EVENT"));
        }
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            handleWizardFinishClick(wiz);
        }
    }

    /**
     * Save or update the event based on the inputs.
     *
     * @param wiz - the WizardDescriptor which contains the inputs.
     */
    private void handleWizardFinishClick(final WizardDescriptor wiz) {
        eventUpdate.setName((String) wiz.getProperty(EVENT_NAME));
        eventUpdate.setDescription((String) wiz.getProperty(EVENT_DESCRIPTION));
        eventUpdate.setEvents((List<Event>) wiz.getProperty(EVENT_EVENTS));
        eventUpdate.setProbability((Distribution) wiz.getProperty(EVENT_PROBABILITY));
        eventUpdate.setProcedures((List<Procedure>) wiz.getProperty(EVENT_PROCEDURES));
        if (isUpdate) {
            service.update(eventUpdate);
        } else {
            eventUpdate.setId(null);
            eventUpdate = service.insert(eventUpdate);
        }
    }

    public void setEvent(Event event) {
        this.isUpdate = true;
        this.eventUpdate = event;
    }
}