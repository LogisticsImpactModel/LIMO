package nl.fontys.sofa.limo.view.wizard.hub;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_COPY;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_TYPE;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NewDuplicatedOrHubTypeHubWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

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
        wiz.putProperty(HUB_TYPE, null);
        wiz.putProperty(HUB_COPY, null);
        Hub hub = getComponent().getHub();
        if (hub != null) {
            wiz.putProperty(HUB_COPY, hub);
        }
        HubType hubType = getComponent().getHubType();
        if (hubType != null) {
            wiz.putProperty(HUB_TYPE, hubType);
        }
    }

    @Override
    public void validate() throws WizardValidationException {
        if (getComponent().rbCopyFrom.isSelected() && getComponent().getHub() == null) {
            throw new WizardValidationException(null, null, ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle").getString("HUB_NOT_SET"));
        }
        if (getComponent().rbFromHubType.isSelected() && getComponent().getHubType() == null) {
            throw new WizardValidationException(null, null, ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle").getString("HUB_TYPE_NOT_SET"));
        }
    }

}
