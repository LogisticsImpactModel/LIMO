package nl.fontys.sofa.limo.view.wizard.leg.normal;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * New or From LegType Wizard
 *
 * @author Pascal Lindner
 */
public class NewOrFromTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private NewOrFromTypePanel component;

    @Override
    public NewOrFromTypePanel getComponent() {
        if (component == null) {
            component = new NewOrFromTypePanel();
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
    }

    //store legType if seleceted
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("legTypeCopy", null);
        LegType legType = getComponent().getLegType();
        if (legType != null) {
            wiz.putProperty("legTypeCopy", legType);
        }
    }
}
