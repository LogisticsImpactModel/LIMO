package nl.fontys.sofa.limo.view.wizard.leg.multimode;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Multimode leg Wizard.
 *
 * @author Pascal Lindner
 */

public class MultimodeLegTableWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private MultimodeLegTablePanel component;

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
    }

    //Save the Map of Legs
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("map", getComponent().getMap());
    }

}
