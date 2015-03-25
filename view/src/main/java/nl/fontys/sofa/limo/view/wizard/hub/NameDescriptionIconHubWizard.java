package nl.fontys.sofa.limo.view.wizard.hub;

import java.text.MessageFormat;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_COPY;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_DESCRIPTION;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_ICON;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_NAME;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_TYPE;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Basic information Panel Wizard for Hub. (Name, Description, Icon)
 *
 * @author Pascal Lindner
 */

public class NameDescriptionIconHubWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionIconPanel component;
    private Hub lastHub = null;
    private HubType lastHubType = null;

    @Override
    public NameDescriptionIconPanel getComponent() {
        if (component == null) {
            component = new NameDescriptionIconPanel(Hub.class);
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

    //Update Labels
    @Override
    public void readSettings(WizardDescriptor wiz) {
        Hub hub = (Hub) wiz.getProperty(HUB_COPY);
        HubType hubType = (HubType) wiz.getProperty(HUB_TYPE);
        if (hub != null) {
            if (hub != lastHub) {
                getComponent().update(hub.getName(), hub.getDescription(), hub.getIcon());
            }
        } else if (hubType != null) {
            if (hubType != lastHubType) {
                getComponent().update("", hubType.getDescription(), hubType.getIcon());
            }
        } else {
            if (lastHub != null || lastHubType != null) {
                getComponent().update("", "", null);
            }
        }
        lastHub = hub;
        lastHubType = hubType;
    }

    //Store name, description and icon
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(HUB_NAME, getComponent().getNameInput());
        wiz.putProperty(HUB_DESCRIPTION, getComponent().getDescriptionInput());
        wiz.putProperty(HUB_ICON, getComponent().getIcon());
    }
    
    //Validate that name is set.
    @Override
    public void validate() throws WizardValidationException {
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, MessageFormat.format(LIMOResourceBundle.getString("VALUE_NOT_SET"), LIMOResourceBundle.getString("NAME")), null);
        }
    }

}
