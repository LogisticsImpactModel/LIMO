package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import java.util.List;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.validation.BeanValidator;
import nl.fontys.sofa.limo.validation.ValidationException;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;

/**
 * Wizard to create Scheduled Leg
 *
 * @author Pascal Lindner
 */
public class ScheduledLegWizard implements WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ScheduledLegPanel component;
    private ScheduledLeg leg;
    private BeanValidator validator = BeanValidator.getInstance();

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
        ScheduledLeg scheduledLeg;
        try {
            scheduledLeg = component.getSchedueldLeg();
            validator.validate(scheduledLeg);
        } catch (NumberFormatException e) {
            throw new WizardValidationException(null, "Expected time or waiting time contains non valid input", null);
        } catch (ValidationException ex) {
            throw new WizardValidationException(null, ex.getMessage(), null);
        }
    }

}
