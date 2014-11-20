package nl.fontys.sofa.limo.view.wizard.types.leg;

import nl.fontys.sofa.limo.view.custom.pane.NameDescriptionIconPanel;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.*;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class NameDescriptionIconLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionIconPanel component;

    @Override
    public NameDescriptionIconPanel getComponent() {
        if (component == null) {
            component = new NameDescriptionIconPanel(LegType.class);
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
        String typeName = (String) wiz.getProperty(TYPE_NAME);
        String typeDescr = (String) wiz.getProperty(TYPE_DESCRIPTION);
        Icon typeIcon = (Icon) wiz.getProperty(TYPE_ICON);
        getComponent().update(typeName, typeDescr, typeIcon);
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(TYPE_NAME, getComponent().getNameInput());
        wiz.putProperty(TYPE_DESCRIPTION, getComponent().getDescriptionInput());
        wiz.putProperty(TYPE_ICON, getComponent().getIcon());
    }

    @Override
    public void validate() throws WizardValidationException {
        ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, MessageFormat.format(bundle.getString("VALUE_NOT_SET"), bundle.getString("NAME")), null);
        }
    }

}
