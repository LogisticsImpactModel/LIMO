package nl.fontys.sofa.limo.view.wizard.leg.multimode;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.validation.BeanValidator;
import nl.fontys.sofa.limo.validation.ValidationException;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;

/**
 * Name Description and Icon for HubType
 *
 * @author Pascal Lindner
 */
public class NameDescriptionIconHubTypeWizard implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionIconPanel component;
    private MultiModeLeg leg;
    private BeanValidator validator = BeanValidator.getInstance();

    @Override
    public NameDescriptionIconPanel getComponent() {
        if (component == null) {
            component = new NameDescriptionIconPanel(Leg.class);
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
    public void readSettings(WizardDescriptor wiz) {
        leg = (MultiModeLeg) wiz.getProperty("leg");
        getComponent().update(leg.getName(), leg.getDescription(), leg.getIcon());
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        leg.setName(getComponent().getNameInput());
        leg.setDescription(getComponent().getDescriptionInput());
        leg.setIcon(getComponent().getIcon());
    }

    //Validate
    @Override
    public void validate() throws WizardValidationException {
        MultiModeLeg tmp = new MultiModeLeg(leg);
        tmp.setName(component.getNameInput());
        try {
            validator.validate(tmp);
        } catch (ValidationException ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
            throw new WizardValidationException(null, LIMOResourceBundle.getString("VALUE_NOT_SET", bundle.getString("NAME")), null);
        }
    }

    @Override
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

}
