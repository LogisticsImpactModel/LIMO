package nl.fontys.sofa.limo.view.wizard.hubtype;

import java.util.List;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_PROCEDURES;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class ProceduresHubTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private ProceduresHubTypePanel component;

    @Override
    public ProceduresHubTypePanel getComponent() {
        if (component == null) {
            component = new ProceduresHubTypePanel();
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
        List<Procedure> procedures = (List<Procedure>) wiz.getProperty(TYPE_PROCEDURES);
        getComponent().update(procedures);
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(TYPE_PROCEDURES, component.getProcedures());
    }

}
