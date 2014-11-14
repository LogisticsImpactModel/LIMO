package nl.fontys.sofa.limo.view.wizard.event;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.custom.pane.ProceduresPanel;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT_PROCEDURES;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class ProceduresWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private ProceduresPanel component;

    @Override
    public ProceduresPanel getComponent() {
        if (component == null) {
            component = new ProceduresPanel();
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
        Event event = (Event) wiz.getProperty(EVENT);
        if (event != null) {
            getComponent().update(event.getProcedures());
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(EVENT_PROCEDURES, getComponent().getProcedures());
    }

}
