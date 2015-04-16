package nl.fontys.sofa.limo.view.wizard.types.hub;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_OLDTYPE;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * New or Duplicated HubType Wizard
 *
 * @author Pascal Lindner
 */

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

    //Store HubType
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        HubType hubType = getComponent().getHubType(); //Selected existing hub type
        if (hubType != null) { //If some hub type is selected (copy from existing hub type)
            wiz.putProperty(TYPE_OLDTYPE, hubType);
        }
    }

}
