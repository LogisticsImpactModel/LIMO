package nl.fontys.sofa.limo.view.wizard.leg.multimode;

import java.util.HashMap;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.view.wizard.types.leg.LegTypeWizardAction;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Multimode leg Wizard.
 *
 * @author Pascal Lindner
 */
public class MultimodeLegTableWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private MultimodeLegTablePanel component;
    private MultiModeLeg leg;

    @Override
    public MultimodeLegTablePanel getComponent() {
        if (component == null) {
            component = new MultimodeLegTablePanel();
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
        leg = (MultiModeLeg) wiz.getProperty(LegTypeWizardAction.TYPE_OLDTYPE);
        if (leg.getLegs() != null) {
            getComponent().getLegModel().addLegs(new HashMap<>(leg.getLegs()));
        }
    }

    //Save the Map of Legs
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        leg.setLegs(getComponent().getLegModel().getMap());
    }

}
