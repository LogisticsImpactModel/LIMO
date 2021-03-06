package nl.fontys.sofa.limo.view.wizard.types.hub;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.wizard.types.leg.LegTypeWizardAction;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Event for HubType wizard
 *
 * @author Pascal Lindner
 */

public class EventHubTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventHubTypePanel component;
    private HubType lastType;
    private HubType hubType;

    @Override
    public EventHubTypePanel getComponent() {
        if (component == null) {
            component = new EventHubTypePanel();
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

    //Update Events if HubType is copied
    @Override
    public void readSettings(WizardDescriptor wiz) {
        hubType = (HubType) wiz.getProperty(LegTypeWizardAction.TYPE_NEWTYPE);
        if (hubType != null) {
            if (hubType != lastType) {
                getComponent().update(hubType.getEvents());
            }
        } else {
            if (lastType != null) {
                getComponent().update(new ArrayList<>());
            }
        }
        lastType = hubType;
    }

    //Store events
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        hubType.setEvents(component.getEvents());
    }
}
