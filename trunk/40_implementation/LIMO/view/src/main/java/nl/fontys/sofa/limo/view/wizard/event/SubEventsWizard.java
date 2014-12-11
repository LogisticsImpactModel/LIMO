package nl.fontys.sofa.limo.view.wizard.event;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT_EVENTS;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class SubEventsWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private SubEventsPanel component;
    private Event lastEvent;

    @Override
    public SubEventsPanel getComponent() {
        if (component == null) {
            component = new SubEventsPanel();
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
        Event event = (Event) wiz.getProperty(EVENT);
        if (event != null) {
            if (event != lastEvent) {
                getComponent().update(event.getEvents());
            }
        } else {
            if(lastEvent != null){
                getComponent().update(new ArrayList<Event>());
            }
        }
        lastEvent = event;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(EVENT_EVENTS, component.getEvents());

    }

}
