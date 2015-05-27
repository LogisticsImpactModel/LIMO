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

    private Event event, originalEvent;
    private boolean update = false;
    private final EventService service = Lookup.getDefault().lookup(EventService.class);

    public EventWizardAction() {
        this.originalEvent = new Event();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            event = new Event();
            panels.add(new NewOrDuplicatedEventWizard());
        } else {
            event = new Event(originalEvent);
        }

        panels.add(new NameDescriptionProbabilityWizard());
        List<Event> events = service.findAll();
        if (!events.isEmpty() && !(events.size() == 1 && events.contains(event))) {
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
        wiz.setTitle(LIMOResourceBundle.getString("EDIT_EVENT"));
        wiz.putProperty("event", event);
        wiz.putProperty("update", update);
        wiz.putProperty("original_event", originalEvent);

        if (!update) {
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
        originalEvent.deepOverwrite((Event) wiz.getProperty("event"));
        
        if (update) {
            service.update(originalEvent);
        } else {
            originalEvent.setId(null);
            originalEvent = service.insert(originalEvent);
        }
    }

    public void setEvent(Event event) {
        this.update = true;
        this.originalEvent = event;
    }
}
