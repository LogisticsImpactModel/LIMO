package nl.fontys.sofa.limo.view.wizard.event;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NewOrDuplicatedEventWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NewOrDuplicatedEventPanel component;

    @Override
    public NewOrDuplicatedEventPanel getComponent() {
        if (component == null) {
            component = new NewOrDuplicatedEventPanel();
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
        // use wiz.getProperty to retrieve previous panel state
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        Event event = getComponent().getEvent();
        if (event != null) {
            wiz.putProperty(EVENT, event);
        }
    }

    @Override
    public void validate() throws WizardValidationException {
        if (getComponent().eventCopySelection.isSelected() && getComponent().getEvent() == null) {
            throw new WizardValidationException(null, null, ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle").getString("EVENT_NOT_SET"));
        }
    }
}
