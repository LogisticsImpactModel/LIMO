package nl.fontys.sofa.limo.view.wizard.procedure;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.view.custom.panel.StandartProceduresPanel;
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
    public void readSettings(WizardDescriptor data) {
    }

    @Override
    public void storeSettings(WizardDescriptor data) {
    }

    @Override
    public void validate() throws WizardValidationException {
    }
}
