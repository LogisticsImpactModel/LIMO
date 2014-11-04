package nl.fontys.sofa.limo.view.wizard.event;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class SubEventsWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private SubEventsPanel component;
    private final ResourceBundle bundle;

    public SubEventsWizard() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");
    }

    @Override
    public SubEventsPanel getComponent() {
        if (component == null) {
            component = new SubEventsPanel();
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
        Object event = wiz.getProperty(bundle.getString("EVENT"));
        if (event != null) {
            getComponent().update((Event) event);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(bundle.getString("EVENT"), getComponent().getEvent());
    }

}