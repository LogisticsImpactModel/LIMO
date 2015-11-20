package nl.fontys.sofa.limo.view.wizard.types.hub;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.panel.ProceduresPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.types.leg.LegTypeWizardAction;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Add Procedure to Hub Type Wizard
 *
 * @author Pascal Lindner
 */

public class ProceduresHubTypeWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ProceduresPanel component;
    private HubType lastType;
    private HubType hubType;

    public ProceduresHubTypeWizard() {
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

    //Uodate procedures
    @Override
    public void readSettings(WizardDescriptor wiz) {
        hubType = (HubType) wiz.getProperty(LegTypeWizardAction.TYPE_NEWTYPE);
        if (hubType != null) {
            if (hubType != lastType) {
                getComponent().update(hubType.getProcedures());
            }
        } else {
            if (lastType != null) {
                getComponent().update(new ArrayList<>());
            }
        }
        lastType = hubType;
    }

    //Store settings
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        hubType.setProcedures(getComponent().getProcedures());
    }

    //Validate
    @Override
    public void validate() throws WizardValidationException {
        if (component.getProcedures().isEmpty()) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("VALUE_NOT_SET2", LIMOResourceBundle.getString("PROCEDURES")));
        }
    }
}
