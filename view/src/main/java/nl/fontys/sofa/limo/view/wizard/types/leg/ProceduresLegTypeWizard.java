package nl.fontys.sofa.limo.view.wizard.types.leg;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.custom.panel.ProceduresPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Add Procedure to LegType
 *
 * @author Pascal Lindner
 */

public class ProceduresLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ProceduresPanel component;
    private LegType lastType;
    private LegType legType;

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

    //Update Procedures
    @Override
    public void readSettings(WizardDescriptor wiz) {
        legType = (LegType) wiz.getProperty(LegTypeWizardAction.TYPE_OLDTYPE);
        if (legType != null) {
            if (legType != lastType) {
                getComponent().update(legType.getProcedures());
            }
        } else {
            if (lastType != null) {
                getComponent().update(new ArrayList<Procedure>());
            }
        }
        lastType = legType;
    }

    //Store settings
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        legType.setProcedures(getComponent().getProcedures());
    }

    //Validate
    @Override
    public void validate() throws WizardValidationException {
        if (component.getProcedures().isEmpty()) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("VALUE_NOT_SET2", LIMOResourceBundle.getString("PROCEDURES")));
        }
    }
}
