package nl.fontys.sofa.limo.view.wizard.types.leg;

import java.text.MessageFormat;
import java.util.ArrayList;
import nl.fontys.sofa.limo.view.custom.panel.ProceduresPanel;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_PROCEDURES;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class ProceduresLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ProceduresPanel component;
    private LegType lastType;

    public ProceduresLegTypeWizard() {
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
        LegType hubType = (LegType) wiz.getProperty(LegTypeWizardAction.TYPE_OLDTYPE);
        if (hubType != null) {
            if (hubType != lastType) {
                getComponent().update(hubType.getProcedures());
            }
        } else {
            if (lastType != null) {
                getComponent().update(new ArrayList<Procedure>());
            }
        }
        lastType = hubType;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(TYPE_PROCEDURES, component.getProcedures());
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getProcedures().isEmpty()) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("VALUE_NOT_SET2", LIMOResourceBundle.getString("PROCEDURES")));
        }
    }
}
