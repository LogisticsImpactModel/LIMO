package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import nl.fontys.sofa.limo.view.custom.panel.NameDescriptionIconPanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Name Description Icon for leg.
 *
 * @author Pascal Lindner
 */

public class NameDescriptionIconLegPanel implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private NameDescriptionIconPanel component;

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
    public void addChangeListener(ChangeListener l) {
    }

    @Override
    public void removeChangeListener(ChangeListener l) {
    }

    // Update legType if selected
    @Override
    public void readSettings(WizardDescriptor wiz) {
        LegType legType = (LegType) wiz.getProperty("legTypeCopy");
        if (legType != null) {
            getComponent().update("", legType.getDescription(), legType.getIcon());
        }
    }

    //Validate
    @Override
    public void validate() throws WizardValidationException {
        if (component.getNameInput().isEmpty()) {
            throw new WizardValidationException(null, LIMOResourceBundle.getString("VALUE_NOT_SET", LIMOResourceBundle.getString("NAME")), null);
        }
    }

    //Store name, description and icon
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("name", getComponent().getNameInput());
        wiz.putProperty("description", getComponent().getDescriptionInput());
        wiz.putProperty("icon", getComponent().getIcon());
    }

}
