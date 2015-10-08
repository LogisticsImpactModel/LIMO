package nl.fontys.sofa.limo.view.wizard.types.leg;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.api.service.provider.LegTypeService;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
import nl.fontys.sofa.limo.view.util.BaseEntityUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Name, Description Icon for LegType.
 *
 * @author Pascal Lindner
 */
public class NameDescriptionIconLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {
    
    private NameDescriptionIconPanel component;
    private LegType legType;
    private LegType originalLegType;
    private boolean update;
    
    @Override
    public NameDescriptionIconPanel getComponent() {
        if (component == null) {
            component = new NameDescriptionIconPanel(LegType.class);
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
        legType = (LegType) wiz.getProperty(LegTypeWizardAction.TYPE_OLDTYPE);
        originalLegType = (LegType) wiz.getProperty("orignal_type");
        update = (boolean) wiz.getProperty("update");
        
        String name = "";
        if (!update) { //When a new leg type is generated, the name should be unique
            if (legType.getName() != null) { //This prevents 'null' as name
                name = BaseEntityUtil.getUniqueName(LegTypeService.class, legType.getName());
            }
        } else { //When a leg type is edited, the name should not be automatically changed
            name = legType.getName();
        }
        
        getComponent().update(name);
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        legType.setName(getComponent().getNameInput());
        legType.setDescription(getComponent().getDescriptionInput());
        legType.setIcon(getComponent().getIcon());
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, LIMOResourceBundle.getString("VALUE_NOT_SET", LIMOResourceBundle.getString("NAME")), null);
        }
        
         if (!update || !originalLegType.getName().equals(getComponent().getNameInput())) {//If the leg type name did not change (while editing) the name should not be uniques
            if (BaseEntityUtil.containsBaseEntityWithName(BaseEntityUtil.getAllEntities(LegTypeService.class), component.getNameInput())) { //Check if name is unique
                getComponent().update(BaseEntityUtil.getUniqueName(LegTypeService.class, getComponent().getNameInput())); //Update leg type name
                throw new WizardValidationException(null, "Leg type name is not unique, a new name is generated.", null);
            }
        }
    }
    
}