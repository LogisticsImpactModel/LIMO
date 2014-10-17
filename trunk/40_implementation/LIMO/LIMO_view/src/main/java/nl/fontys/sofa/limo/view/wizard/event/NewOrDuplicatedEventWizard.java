package nl.fontys.sofa.limo.view.wizard.event;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NewOrDuplicatedEventWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private NewOrDuplicatedEventPanel component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
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
        if (getComponent().eventCopySelection.isSelected()) {
            wiz.putProperty(java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("EVENT"), getComponent().getEvent());
        }
    }

    @Override
    public void validate() throws WizardValidationException {
        if (getComponent().eventCopySelection.isSelected() && getComponent().getEvent() == null) {
            throw new WizardValidationException(null, null, "Event not set!");
        }
    }
}
