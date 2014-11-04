package nl.fontys.sofa.limo.view.wizard.event;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NameDescriptionProbabilityWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionProbabilityPanel component;
    private final ResourceBundle bundle;
    private boolean isValid = true;

    public NameDescriptionProbabilityWizard() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");
    }

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
        return isValid;
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        Object event = wiz.getProperty(bundle.getString("EVENT")
        );
        if (event != null) {
            getComponent().update((Event) event);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(bundle.getString("EVENT"), getComponent().getEvent());
    }

    @Override
    public void validate() throws WizardValidationException {
        NameDescriptionProbabilityPanel comp = getComponent();
        if (comp.nameTextField.getText().isEmpty()) {
            isValid = false;
            throw new WizardValidationException(comp, null, bundle.getString("NAME_IS_NOT_SET!"));
        } else {
            isValid = true;
        }
    }

}
