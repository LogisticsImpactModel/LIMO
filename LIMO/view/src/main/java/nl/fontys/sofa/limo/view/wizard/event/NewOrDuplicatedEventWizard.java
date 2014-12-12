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

    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(EVENT, null);
        Event event = getComponent().getEvent();
        if (event != null) {
            wiz.putProperty(EVENT, event);
        }
    }

    @Override
    public void validate() throws WizardValidationException {
        if (getComponent().isEventCopySelected() && getComponent().getEvent() == null) {
            throw new WizardValidationException(null, null, ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle").getString("EVENT_NOT_SET"));
        }
    }
}
