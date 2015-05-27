package nl.fontys.sofa.limo.view.wizard.event;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Wizard which uses the NewOrDuplicatedEventPanel to choose if you want to
 * create a new event or choose an existing as base.
 *
 * @author Sven Mäurer
 */
public class NewOrDuplicatedEventWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NewOrDuplicatedEventPanel component;
    private Event event;

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
        event = (Event) wiz.getProperty("event");
    }

    /**
     * Save data of event to copy from if copy is selected.
     *
     * @param wiz the WizardDescriptor.
     */
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        Event copyFromEvent = getComponent().getEvent();
        if (copyFromEvent != null) {
            wiz.putProperty("event", new Event(copyFromEvent));
        }
    }

    @Override
    public void validate() throws WizardValidationException {
        if (getComponent().isEventCopySelected() && getComponent().getEvent() == null) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("EVENT_NOT_SET"));
        }
    }
}
