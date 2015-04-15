package nl.fontys.sofa.limo.view.wizard.types.leg;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_OLDTYPE;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Selection New or Copy LegType wizard
 *
 * @author Pascal Lindner
 */

public class NewOrDuplicatedLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private NewOrDuplicatedLegTypePanel component;

    @Override
    public NewOrDuplicatedLegTypePanel getComponent() {
        if (component == null) {
            component = new NewOrDuplicatedLegTypePanel();
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

    //Store legType
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        LegType legType = getComponent().getLegType(); 
        if (legType != null) { //If a leg type is selected (copy from existing leg type)
            wiz.putProperty(TYPE_OLDTYPE, legType);
        }
    }

}
