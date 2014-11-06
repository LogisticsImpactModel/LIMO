package nl.fontys.sofa.limo.view.wizard.event;

import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class ProceduresWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private ProceduresPanel component;
    private Event event;

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
        event = (Event) wiz.getProperty(EVENT);
        getComponent().update(event.getProcedures());
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        event.setProcedures(getComponent().getProcedures());
        wiz.putProperty(EVENT, event);
    }

}
