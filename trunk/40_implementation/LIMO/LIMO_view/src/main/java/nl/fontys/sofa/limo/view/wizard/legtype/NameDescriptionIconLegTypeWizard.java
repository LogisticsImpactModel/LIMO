package nl.fontys.sofa.limo.view.wizard.legtype;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.Icon;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NameDescriptionIconLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {
    
    private NameDescriptionIconLegTypePanel component;
    
    @Override
    public NameDescriptionIconLegTypePanel getComponent() {
        if (component == null) {
            component = new NameDescriptionIconLegTypePanel();
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
        // If it is always OK to press Next or Finish, then:
        return true;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }
    
    @Override
    public void addChangeListener(ChangeListener l) {
    }
    
    @Override
    public void removeChangeListener(ChangeListener l) {
    }
    
    @Override
    public void readSettings(WizardDescriptor wiz) {
        String legTypeName = (String) wiz.getProperty(LegTypeWizardAction.LEG_TYPE_NAME);
        String legTypeDescr = (String) wiz.getProperty(LegTypeWizardAction.LEG_TYPE_DESCRIPTION);
        Icon legTypeIcon = (Icon) wiz.getProperty(LegTypeWizardAction.LEG_TYPE_ICON);
        getComponent().update(legTypeName, legTypeDescr, legTypeIcon);
    }
    
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(LegTypeWizardAction.LEG_TYPE_NAME, getComponent().getLegTypeName());
        wiz.putProperty(LegTypeWizardAction.LEG_TYPE_DESCRIPTION, getComponent().getLegTypeDescr());
        wiz.putProperty(LegTypeWizardAction.LEG_TYPE_ICON, getComponent().getLegTypeIcon());
    }
    
    @Override
    public void validate() throws WizardValidationException {
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        if (component.getLegTypeName().isEmpty()) {
            throw new WizardValidationException(null, MessageFormat.format(bundle.getString("VALUE_NOT_SET"), bundle.getString("NAME")), null);
        }
    }
    
}
