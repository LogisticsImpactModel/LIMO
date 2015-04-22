package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

/**
 * Wizard to create Scheduled Leg
 *
 * @author Pascal Lindner
 */
public class ScheduledLegWizard implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ScheduledLegPanel component;
//    private boolean isValid = false;

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

    @Override
    public void validate() throws WizardValidationException {
        ScheduledLeg schedueldLeg;
        try {
            schedueldLeg = component.getSchedueldLeg();
        } catch (NumberFormatException e) {
            throw new WizardValidationException(null, "Expected time or waiting time contains non valid input", null);
        }

        if (schedueldLeg.getExpectedTime() <= 0 || schedueldLeg.getWaitingTimeLimit() <= 0) {
            throw new WizardValidationException(null, "Expected time or waiting time needs to be larger than 0", null);
        } else if (schedueldLeg.getAlternative() == null) {
            throw new WizardValidationException(null, "Alternative leg is missing", null);
        } else if (schedueldLeg.getAcceptanceTimes().isEmpty()) {
            throw new WizardValidationException(null, "Acceptance time is missing", null);
        }
    }

}
