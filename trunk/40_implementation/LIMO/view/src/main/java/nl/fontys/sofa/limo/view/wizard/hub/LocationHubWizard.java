package nl.fontys.sofa.limo.view.wizard.hub;

import java.text.MessageFormat;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_COPY;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_LOCATION;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_TYPE;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class LocationHubWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private LocationHubPanel component;
    private Hub lastHub;
    private HubType lastHubType;

    @Override
    public LocationHubPanel getComponent() {
        if (component == null) {
            component = new LocationHubPanel();
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
        Hub hub = (Hub) wiz.getProperty(HUB_COPY);
        HubType hubType = (HubType) wiz.getProperty(HUB_TYPE);
        if (hub != null) {
            if (hub != lastHub) {
                getComponent().updateLabel(null);
                getComponent().updateLabel(hub.getLocation());
            }
        } else if (hubType != null) {
            if (hubType != lastHubType) {
                getComponent().updateLabel(null);
            }
        } else {
            if (lastHub != null || lastHubType != null) {
                getComponent().updateLabel(null);
            }
        }
        lastHub = hub;
        lastHubType = hubType;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(HUB_LOCATION, getComponent().getHubLocation());
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getHubLocation() == null) {
            throw new WizardValidationException(null, MessageFormat.format(LIMOResourceBundle.getString("VALUE_NOT_SET"), LIMOResourceBundle.getString("CONTINENT")), null);
        }
    }

}
