package nl.fontys.sofa.limo.view.wizard.event;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.custom.panel.ProceduresPanel;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT;
import static nl.fontys.sofa.limo.view.wizard.event.EventWizardAction.EVENT_PROCEDURES;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class ProceduresWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ProceduresPanel component;
    private final ResourceBundle bundle;
    private Event lastEvent;

    public ProceduresWizard() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/Bundle");
    }

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
            if (event != lastEvent) {
                getComponent().update(event.getProcedures());
            }
        } else {
            if(lastEvent != null){
                getComponent().update(new ArrayList<Procedure>());
            }
        }
        lastEvent = event;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(EVENT_PROCEDURES, getComponent().getProcedures());
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getProcedures().isEmpty()) {
            throw new WizardValidationException(null, null, MessageFormat.format(bundle.getString("VALUE_NOT_SET2"), bundle.getString("PROCEDURES")));
        }
    }

}
