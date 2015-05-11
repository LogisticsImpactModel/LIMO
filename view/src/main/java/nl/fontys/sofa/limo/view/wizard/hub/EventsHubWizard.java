package nl.fontys.sofa.limo.view.wizard.hub;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Wizard for events in Hub. Responsible for store and read settings.
 *
 * @author Pascal Lindner
 */
public class EventsHubWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventsHubPanel component;
    private Hub hub;

    @Override
    public EventsHubPanel getComponent() {
        if (component == null) {
            component = new EventsHubPanel();
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
        hub = (Hub) wiz.getProperty("hub");
        getComponent().update(new ArrayList(hub.getEvents()));
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        hub.setEvents(component.getEvents());
    }

}
