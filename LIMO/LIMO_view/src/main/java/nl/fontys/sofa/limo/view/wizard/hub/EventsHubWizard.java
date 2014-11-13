package nl.fontys.sofa.limo.view.wizard.hub;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class EventsHubWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventsHubPanel component;

    @Override
    public EventsHubPanel getComponent() {
        if (component == null) {
            component = new EventsHubPanel();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
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
        Hub hub = (Hub) wiz.getProperty("hubCopy");
        if (hub != null) {
            getComponent().update(hub.getEvents());
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("events", component.getEvents());
    }

}
