package nl.fontys.sofa.limo.view.wizard.procedure;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.custom.panel.StandartProceduresPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Procedure Wizard for Hub.
 *
 * @author Pascal Lindner
 */
public class ProcedureWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private StandartProceduresPanel component;
    private Hub hub;

    public ProcedureWizard() {
    }

    @Override
    public StandartProceduresPanel getComponent() {
        if (component == null) {
            component = new StandartProceduresPanel();
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
        hub = (Hub) wiz.getProperty("hub");
        if (hub != null) {
            getComponent().update(hub.getProcedures());
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        hub.setProcedures(new ArrayList(getComponent().getProcedures()));
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getProcedures().isEmpty()) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("VALUE_NOT_SET2", LIMOResourceBundle.getString("PROCEDURES")));
        }
    }

}
