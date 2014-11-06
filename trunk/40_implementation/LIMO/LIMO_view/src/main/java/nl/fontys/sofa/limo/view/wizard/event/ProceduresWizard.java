package nl.fontys.sofa.limo.view.wizard.event;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class ProceduresWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private ProceduresPanel component;
    private final ResourceBundle bundle;
    private Event event;

    public ProceduresWizard() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");
    }

    @Override
    public ProceduresPanel getComponent() {
        if (component == null) {
            component = new ProceduresPanel();
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
        event = (Event) wiz.getProperty(bundle.getString("EVENT"));
        getComponent().update(event.getProcedures());
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        event.setProcedures(getComponent().getProcedures());
        wiz.putProperty(bundle.getString("EVENT"), event);
    }

}
