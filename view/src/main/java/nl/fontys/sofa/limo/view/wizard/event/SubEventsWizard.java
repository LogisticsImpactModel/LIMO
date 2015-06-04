package nl.fontys.sofa.limo.view.wizard.event;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Wizard which uses the SubEventsPanel to add sub events to the event.
 *
 * @author Sven Mäurer
 */
public class SubEventsWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private SubEventsPanel component;
    private Event event;

    @Override
    public SubEventsPanel getComponent() {
        if (component == null) {
            component = new SubEventsPanel();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        event = (Event) wiz.getProperty("event");
        getComponent().update(new ArrayList(event.getEvents()));
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        event.setEvents(getComponent().getEvents());
    }
}
