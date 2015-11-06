package nl.fontys.sofa.limo.view.wizard.types.leg;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

/**
 * Event LegType Wizard
 *
 * @author Pascal Lindner
 */

public class EventLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventLegTypePanel component;
    private LegType lastType;
    private LegType legType;

    @Override
    public EventLegTypePanel getComponent() {
        if (component == null) {
            component = new EventLegTypePanel();
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

    //Update Events
    @Override
    public void readSettings(WizardDescriptor wiz) {
        legType = (LegType) wiz.getProperty(LegTypeWizardAction.TYPE_NEWTYPE);
        if (legType != null) {
            if (legType != lastType) {
                getComponent().update(legType.getEvents());
            }
        } else {
            if (lastType != null) {
                getComponent().update(new ArrayList<Event>());
            }
        }
        lastType = legType;
    }

    //Store events
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        legType.setEvents(component.getEvents());
    }
}
