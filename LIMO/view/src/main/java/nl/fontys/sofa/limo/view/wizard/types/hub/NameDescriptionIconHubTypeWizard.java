package nl.fontys.sofa.limo.view.wizard.types.hub;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.types.leg.LegTypeWizardAction;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Name Description and Icon for HubType
 *
 * @author Pascal Lindner
 */

public class NameDescriptionIconHubTypeWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionIconPanel component;
    private HubType lastType;

    @Override
    public NameDescriptionIconPanel getComponent() {
        if (component == null) {
            component = new NameDescriptionIconPanel(HubType.class);
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
        HubType hubType = (HubType) wiz.getProperty(LegTypeWizardAction.TYPE_OLDTYPE);
        if (hubType != null) {
            if (hubType != lastType) {
                getComponent().update(hubType.getName(), hubType.getDescription(), hubType.getIcon());
            }
        } else {
            if (lastType != null) {
                getComponent().update("", "", null);
            }
        }
        lastType = hubType;
    }

    //Store Name, Description and Icon
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(HubTypeWizardAction.TYPE_NAME, getComponent().getNameInput());
        wiz.putProperty(HubTypeWizardAction.TYPE_DESCRIPTION, getComponent().getDescriptionInput());
        wiz.putProperty(HubTypeWizardAction.TYPE_ICON, getComponent().getIcon());
    }

    //Validate
    @Override
    public void validate() throws WizardValidationException {
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, LIMOResourceBundle.getString("VALUE_NOT_SET", bundle.getString("NAME")), null);
        }
    }

}
