package nl.fontys.sofa.limo.view.wizard.types.hub;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.api.service.provider.HubTypeService;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
import nl.fontys.sofa.limo.view.util.BaseEntityUtil;
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
    private boolean update;
    private HubType hubType;
    private HubType originalHubType;

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
        hubType = (HubType) wiz.getProperty(LegTypeWizardAction.TYPE_OLDTYPE);
        originalHubType = (HubType) wiz.getProperty("orignal_type");
        update = (boolean) wiz.getProperty("update");
        
        String name = "";
        if (!update) { //When a new hub type is generated, the name should be unique
            if (hubType.getName() != null) { //This prevents 'null' as name
                name = BaseEntityUtil.getUniqueName(HubTypeService.class, hubType.getName());
            }
        } else { //When a hub type is edited, the name should not be automatically changed
            name = hubType.getName();
        }
        
        getComponent().update(name);
    }

    //Store Name, Description and Icon
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        hubType.setName(getComponent().getNameInput());
        hubType.setDescription(getComponent().getDescriptionInput());
        hubType.setIcon(getComponent().getIcon());
    }

    //Validate
    @Override
    public void validate() throws WizardValidationException {
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, LIMOResourceBundle.getString("VALUE_NOT_SET", bundle.getString("NAME")), null);
        }
        
        if (!update || !originalHubType.getName().equals(getComponent().getNameInput())) {//If the hub name did not change (while editing) the name should not be uniques
            if (BaseEntityUtil.containsBaseEntityWithName(BaseEntityUtil.getAllEntities(HubTypeService.class), component.getNameInput())) { //Check if name is unique
                getComponent().update(BaseEntityUtil.getUniqueName(HubTypeService.class, getComponent().getNameInput())); //Update hub name
                throw new WizardValidationException(null, "Hub type name is not unique, a new name is generated.", null);
            }
        }
    }

}
