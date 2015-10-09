package nl.fontys.sofa.limo.view.wizard.hub;

import java.text.MessageFormat;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.validation.BeanValidator;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;

/**
 * Location Wizard for Hub.
 *
 * @author Pascal Lindner
 */
public class LocationHubWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private LocationHubPanel component;
    private Hub hub;
    private BeanValidator validator = BeanValidator.getInstance();

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
        hub = (Hub) wiz.getProperty("hub");
        if (hub != null) {
            getComponent().updateLabel(null);
            getComponent().updateLabel(hub.getLocation());
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        hub.setLocation(getComponent().getHubLocation());
    }

    @Override
    public void validate() throws WizardValidationException {
        Hub hub = new Hub(this.hub);
        hub.setLocation(component.getHubLocation());
        try {
            validator.validate(hub);
        } catch (ValidationException ex) {
            throw new WizardValidationException(null, MessageFormat.format(LIMOResourceBundle.getString("VALUE_NOT_SET"), LIMOResourceBundle.getString("CONTINENT")), null);
        }
    }

}
