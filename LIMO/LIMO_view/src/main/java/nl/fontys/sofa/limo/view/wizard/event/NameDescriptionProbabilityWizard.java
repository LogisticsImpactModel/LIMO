package nl.fontys.sofa.limo.view.wizard.event;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.*;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NameDescriptionProbabilityWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionProbabilityPanel component;

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
            getComponent().update((Event) event);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(EVENT_NAME, getComponent().getNameInput());
        wiz.putProperty(EVENT_DESCRIPTION, getComponent().getDescriptionInput());
        wiz.putProperty(EVENT_PROCEDURES, getComponent().getProbability());
    }

    @Override
    public void validate() throws WizardValidationException {
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, MessageFormat.format(bundle.getString("VALUE_NOT_SET"), bundle.getString("NAME")), null);
        }
    }

}
