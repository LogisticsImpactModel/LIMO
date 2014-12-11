package nl.fontys.sofa.limo.view.wizard.hub;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.*;
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
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        if (component.getHubLocation() == null) {
            throw new WizardValidationException(null, MessageFormat.format(bundle.getString("VALUE_NOT_SET"), bundle.getString("CONTINENT")), null);
        }
    }

}
