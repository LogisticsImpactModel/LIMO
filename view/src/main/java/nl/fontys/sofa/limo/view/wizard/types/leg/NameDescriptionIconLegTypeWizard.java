package nl.fontys.sofa.limo.view.wizard.types.leg;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
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
    private LegType lastType;
    private LegType legType;
    
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

    //Update Name, Description and Icon
    @Override
    public void readSettings(WizardDescriptor wiz) {
        legType = (LegType) wiz.getProperty(LegTypeWizardAction.TYPE_OLDTYPE);
        if (legType != null) {
            if (legType != lastType) {
                getComponent().update(legType.getName(), legType.getDescription(), legType.getIcon());
            }
        } else {
            if (lastType != null) {
                getComponent().update("", "", null);
            }
        }
        lastType = legType;
    }

    //Store name, description and input
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        legType.setName(getComponent().getNameInput());
        legType.setDescription(getComponent().getDescriptionInput());
        legType.setIcon(getComponent().getIcon());
    }

    //Validate
    @Override
    public void validate() throws WizardValidationException {
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, LIMOResourceBundle.getString("VALUE_NOT_SET", LIMOResourceBundle.getString("NAME")), null);
        }
    }
    
}
