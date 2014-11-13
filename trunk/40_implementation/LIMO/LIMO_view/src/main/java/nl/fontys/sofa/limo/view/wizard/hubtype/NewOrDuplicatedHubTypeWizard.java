package nl.fontys.sofa.limo.view.wizard.hubtype;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_DESCRIPTION;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_ICON;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_NAME;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_PROCEDURES;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class NewOrDuplicatedHubTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private NewOrDuplicatedHubTypePanel component;

    @Override
    public NewOrDuplicatedHubTypePanel getComponent() {
        if (component == null) {
            component = new NewOrDuplicatedHubTypePanel();
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
        HubType hubType = getComponent().getHubType();
        if (hubType != null) {
            wiz.putProperty(TYPE_NAME, hubType.getName());
            wiz.putProperty(TYPE_DESCRIPTION, hubType.getDescription());
            wiz.putProperty(TYPE_ICON, hubType.getIcon());
            wiz.putProperty(TYPE_PROCEDURES, hubType.getProcedures());
        }
    }

}
