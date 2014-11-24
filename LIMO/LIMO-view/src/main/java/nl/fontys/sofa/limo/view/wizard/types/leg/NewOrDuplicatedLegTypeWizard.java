package nl.fontys.sofa.limo.view.wizard.types.leg;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_DESCRIPTION;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_ICON;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_NAME;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_PROCEDURES;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

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
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
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
        // use wiz.getProperty to retrieve previous panel state
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        LegType legType = getComponent().getLegType();
        if (legType != null) {
            wiz.putProperty(TYPE_NAME, legType.getName());
            wiz.putProperty(TYPE_DESCRIPTION, legType.getDescription());
            wiz.putProperty(TYPE_ICON, legType.getIcon());
            wiz.putProperty(TYPE_PROCEDURES, legType.getProcedures());
        }
    }

}
