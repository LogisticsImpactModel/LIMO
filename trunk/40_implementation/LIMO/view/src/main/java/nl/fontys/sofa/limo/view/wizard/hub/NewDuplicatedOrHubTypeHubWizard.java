package nl.fontys.sofa.limo.view.wizard.hub;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_COPY;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_TYPE;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NewDuplicatedOrHubTypeHubWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    /**
     * Evaluate panel data for Hub.
     *
     * @author Pascal Lindner
     */
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
    //Store Copy or HubType
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
    //Validate for empty selection
    @Override
    public void validate() throws WizardValidationException {
        if (getComponent().isHubCopySelected() && getComponent().getHub() == null) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("HUB_NOT_SET"));
        }
        if (getComponent().isHubTypeSelected() && getComponent().getHubType() == null) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("HUB_TYPE_NOT_SET"));
        }
    }

}
