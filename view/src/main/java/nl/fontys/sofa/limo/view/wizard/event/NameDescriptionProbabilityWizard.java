package nl.fontys.sofa.limo.view.wizard.event;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Wizard which uses the NameDescriptionProbabilityPanel to enter name,
 * description and probability.
 *
 * @author Sven Mäurer
 */
public class NameDescriptionProbabilityWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionProbabilityPanel component;
    private Event event;

    @Override
    public NameDescriptionProbabilityPanel getComponent() {
        if (component == null) {
            component = new NameDescriptionProbabilityPanel();
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

    /**
     * Get data of event to edit or copy from.
     *
     * @param wiz the WizardDescriptor.
     */
    @Override
    public void readSettings(WizardDescriptor wiz) {
        event = (Event) wiz.getProperty("event");
        getComponent().updateEvent(event);
    }

    /**
     * Set data entered in the panel in the WizardDescriptor.
     *
     * @param wiz the WizardDescriptor.
     */
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        event.setName(getComponent().getNameInput());
        event.setDescription(getComponent().getDescriptionInput());
        event.setProbability(getComponent().getProbability());
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, LIMOResourceBundle.getString("VALUE_NOT_SET", LIMOResourceBundle.getString("NAME")), null);
        }
    }

}
