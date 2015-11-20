package nl.fontys.sofa.limo.view.wizard.hub;

import java.text.MessageFormat;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.validation.BeanValidator;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
import nl.fontys.sofa.limo.view.util.BaseEntityUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Basic information Panel Wizard for Hub. (Name, Description, Icon)
 *
 * @author Pascal Lindner
 */
public class NameDescriptionIconHubWizard implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionIconPanel component;
    private Hub hub, originalHub;
    private boolean update;
    private final BeanValidator validator = BeanValidator.getInstance();

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
        return true; //Next button is enabled
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        hub = (Hub) wiz.getProperty("hub");
        originalHub = (Hub) wiz.getProperty("original_hub");
        update = (boolean) wiz.getProperty("update");

        String name = "";
        if (!update) { //When a new hub is generated, the name should be unique
            if (hub.getName() != null) { //This prevents 'null' as name
                name = BaseEntityUtil.getUniqueName(HubService.class, hub.getName());
            }
        } else { //When a hub is edited, the name should not be unique 
            name = hub.getName();
        }

        getComponent().update(name, hub.getDescription(), hub.getIcon());
    }

    //Store name, description and icon
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        hub.setName(getComponent().getNameInput());
        hub.setDescription(getComponent().getDescriptionInput());
        hub.setIcon(getComponent().getIcon());
    }

    //Validate that name is set.
    @Override
    public void validate() throws WizardValidationException {
        Hub tmp = new Hub(this.hub);
        tmp.setDescription(getComponent().getDescriptionInput());
        tmp.setName(getComponent().getNameInput());
        tmp.setIcon(getComponent().getIcon());
        if(tmp.getLocation() == null){tmp.setLocation(new Location());}
        try {
            validator.validate(tmp);
        } catch (ValidationException ex) {
            throw new WizardValidationException(null, MessageFormat.format(LIMOResourceBundle.getString("VALUE_NOT_SET"), LIMOResourceBundle.getString("NAME")), null);
        }
        if (!update || !originalHub.getName().equals(getComponent().getNameInput())) {//If the hub name did not change (while editing) the name should not be uniques
            if (BaseEntityUtil.containsBaseEntityWithName(BaseEntityUtil.getAllEntities(HubService.class), getComponent().getNameInput())) { //Check if name is unique
                getComponent().update(BaseEntityUtil.getUniqueName(HubService.class, getComponent().getNameInput())); //Update hub name
                throw new WizardValidationException(null, "Hub name is not unique, a new hub name is generated.", null);
            }
        }
    }

}
