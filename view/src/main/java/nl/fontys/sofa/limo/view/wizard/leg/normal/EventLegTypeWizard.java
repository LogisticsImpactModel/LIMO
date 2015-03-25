package nl.fontys.sofa.limo.view.wizard.leg.normal;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Event for leg Wizard
 *
 * @author Pascal Lindner
 */

public class EventLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventsLegTypePanel component;

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
    
    //Update label
    @Override
    public void readSettings(WizardDescriptor wiz) {
        Leg leg = (Leg) wiz.getProperty("leg");
        if (leg != null) {
            getComponent().update(leg.getEvents());
        } LegType htyp = (LegType) wiz.getProperty("legTypeCopy");
            if (htyp != null) {
                getComponent().update(htyp.getEvents());
            }
    }

    //Store events
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("events", component.getEvents());
    }

}
