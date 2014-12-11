package nl.fontys.sofa.limo.view.wizard.types.hub;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_OLDTYPE;
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
        wiz.putProperty(TYPE_OLDTYPE, null);
        HubType hubType = getComponent().getHubType();
        if (hubType != null) {
            wiz.putProperty(TYPE_OLDTYPE, hubType);
        }
    }

}
