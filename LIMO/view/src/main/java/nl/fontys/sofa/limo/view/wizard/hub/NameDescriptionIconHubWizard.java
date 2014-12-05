package nl.fontys.sofa.limo.view.wizard.hub;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.*;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NameDescriptionIconHubWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionIconPanel component;

    @Override
    public NameDescriptionIconPanel getComponent() {
        if (component == null) {
            component = new NameDescriptionIconPanel(Hub.class);
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
        Hub hub = (Hub) wiz.getProperty(HUB_COPY);
        if (hub != null) {
            getComponent().update(hub.getName(), hub.getDescription(), hub.getIcon());
        }
        HubType hubType = (HubType) wiz.getProperty(HUB_TYPE);
        if (hubType != null) {
            getComponent().update("", hubType.getDescription(), hubType.getIcon());
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(HUB_NAME, getComponent().getNameInput());
        wiz.putProperty(HUB_DESCRIPTION, getComponent().getDescriptionInput());
        wiz.putProperty(HUB_ICON, getComponent().getIcon());
    }

    @Override
    public void validate() throws WizardValidationException {
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, MessageFormat.format(bundle.getString("VALUE_NOT_SET"), bundle.getString("NAME")), null);
        }
    }

}
