package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import java.util.List;
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
    private ScheduledLeg leg;

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
        leg = (ScheduledLeg) wiz.getProperty("leg");
        getComponent().update(leg.getExpectedTime(), leg.getWaitingTimeLimit(), leg.getAcceptanceTimes(), leg.getAlternative());
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        try {
            List<Long> times = getComponent().getAcceptanceTimes();

            if (times.size() > 0) {
                leg.setAcceptanceTimes(times);
            }
            if (getComponent().getAlternativeLeg() != null) {
                leg.setAlternative(getComponent().getAlternativeLeg());
            }

            leg.setExpectedTime(getComponent().getExpcetedTime()); //This can throw a NumberFormatException when non-numaric values are inserted
            leg.setWaitingTimeLimit(getComponent().getWaitingTimeLimit());
        } catch (Exception e) {

        }
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
