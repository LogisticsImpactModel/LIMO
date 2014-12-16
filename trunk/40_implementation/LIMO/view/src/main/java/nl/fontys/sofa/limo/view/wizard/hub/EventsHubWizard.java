package nl.fontys.sofa.limo.view.wizard.hub;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

    /**
     * Wizard for events in Hub. Responsible for store and read settings.
     * @author Pascal Lindner
     */

public class EventsHubWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventsHubPanel component;
    private Hub lastHub;
    private HubType lastHubType;

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

    //Set text for the panel if exists a HubCopy, HubType oder Hub itself for update.
    @Override
    public void readSettings(WizardDescriptor wiz) {
        Hub hub = (Hub) wiz.getProperty(HubWizardAction.HUB_COPY);
        HubType hubType = (HubType) wiz.getProperty(HubWizardAction.HUB_TYPE);
        if (hub != null) {
            if (hub != lastHub) {
                getComponent().update(hub.getEvents());
            }
        } else if (hubType != null) {
            if (hubType != lastHubType) {
                getComponent().update(hubType.getEvents());
            }
        } else {
            if (lastHub != null || lastHubType != null) {
                getComponent().update(new ArrayList<Event>());
            }
        }
        lastHub = hub;
        lastHubType = hubType;
    }
    //Store settings
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(HubWizardAction.HUB_EVENTS, component.getEvents());
    }

}
