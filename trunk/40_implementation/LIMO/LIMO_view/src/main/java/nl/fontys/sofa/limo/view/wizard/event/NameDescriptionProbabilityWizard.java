package nl.fontys.sofa.limo.view.wizard.event;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NameDescriptionProbabilityWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private NameDescriptionProbabilityPanel component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public NameDescriptionProbabilityPanel getComponent() {
        if (component == null) {
            component = new NameDescriptionProbabilityPanel();
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
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        Object event = wiz.getProperty(java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("EVENT"));
        if (event != null) {
            getComponent().update((Event) event);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("EVENT"), getComponent().getEvent());
    }

    @Override
    public void validate() throws WizardValidationException {
        NameDescriptionProbabilityPanel comp = getComponent();
        if (comp.tfName.getText().isEmpty()) {
            throw new WizardValidationException(comp, null, "Name is not set!");
        }
    }

}
