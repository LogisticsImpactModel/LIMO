package nl.fontys.sofa.limo.view.wizard.event;

import java.util.List;
import nl.fontys.sofa.limo.view.custom.pane.EventsPanel;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class SubEventsWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private SubEventsPanel component;

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
            getComponent().update(event);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        List<Event> event = getComponent().getEvents();
        wiz.putProperty(EVENT, event);
    }

}
