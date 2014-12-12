package nl.fontys.sofa.limo.view.wizard.event;

import java.text.MessageFormat;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT_DESCRIPTION;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT_NAME;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT_PROBABILITY;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NameDescriptionProbabilityWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionProbabilityPanel component;
    private Event lastEvent;

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

    @Override
    public void readSettings(WizardDescriptor wiz) {
        Event event = (Event) wiz.getProperty(EVENT);
        if (event != null) {
            if (event != lastEvent) {
                getComponent().updateEvent((Event) event);
            }
        } else {
            if (lastEvent != null) {
                getComponent().updateEvent(null);
            }
        }
        lastEvent = event;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(EVENT_NAME, getComponent().getNameInput());
        wiz.putProperty(EVENT_DESCRIPTION, getComponent().getDescriptionInput());
        wiz.putProperty(EVENT_PROBABILITY, getComponent().getProbability());
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, LIMOResourceBundle.getString("VALUE_NOT_SET", LIMOResourceBundle.getString("NAME")), null);
        }
    }

}
