package nl.fontys.sofa.limo.view.wizard.leg.normal;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Event for leg Wizard
 *
 * @author Pascal Lindner
 */
public class EventLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventsLegTypePanel component;
    private Leg leg;

    @Override
    public EventsLegTypePanel getComponent() {
        if (component == null) {
            component = new EventsLegTypePanel();
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
        leg = (Leg) wiz.getProperty("leg");
        getComponent().update(new ArrayList(leg.getEvents()));
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        leg.setEvents(component.getEvents());
    }

}
