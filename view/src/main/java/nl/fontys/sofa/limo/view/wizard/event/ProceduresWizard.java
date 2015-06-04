package nl.fontys.sofa.limo.view.wizard.event;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.custom.panel.ProceduresPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Wizard which uses the ProceduresPanel to add procedures to the event.
 *
 * @author Sven Mäurer
 */
public class ProceduresWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ProceduresPanel component;
    private Event event;

    public ProceduresWizard() {
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
        event = (Event) wiz.getProperty("event");
        getComponent().update(new ArrayList(event.getProcedures()));
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        event.setProcedures(new ArrayList(getComponent().getProcedures()));
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getProcedures().isEmpty()) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("VALUE_NOT_SET2", LIMOResourceBundle.getString("PROCEDURES")));
        }
    }

}
