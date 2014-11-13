package nl.fontys.sofa.limo.view.wizard.hub;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_COPY;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_TYPE;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class NewDuplicatedOrHubTypeHubWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private NewDuplicatedOrHubTypeHubPanel component;

    @Override
    public NewDuplicatedOrHubTypeHubPanel getComponent() {
        if (component == null) {
            component = new NewDuplicatedOrHubTypeHubPanel();
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
        Hub hub = getComponent().getHub();
        if (hub != null) {
            wiz.putProperty(HUB_COPY, hub);
        }
        HubType hubType = getComponent().getHubType();
        if (hubType != null) {
            wiz.putProperty(HUB_TYPE, hubType);
        }
    }

}
