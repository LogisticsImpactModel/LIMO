package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Wizard to create Scheduled Leg
 *
 * @author Pascal Lindner
 */

public class ScheduledLegWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private ScheduledLegPanel component;

    @Override
    public ScheduledLegPanel getComponent() {
        if (component == null) {
            component = new ScheduledLegPanel();
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

    //Store leg
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("leg", component.getSchedueldLeg());
    }

}
