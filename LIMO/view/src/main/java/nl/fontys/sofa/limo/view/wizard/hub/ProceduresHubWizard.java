package nl.fontys.sofa.limo.view.wizard.hub;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.panel.ProceduresPanel;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_COPY;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_PROCEDURES;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.HUB_TYPE;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class ProceduresHubWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ProceduresPanel component;
    private Hub lastHub;
    private HubType lastHubType;

    public ProceduresHubWizard() {
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
        Hub hub = (Hub) wiz.getProperty(HUB_COPY);
        HubType hubType = (HubType) wiz.getProperty(HUB_TYPE);
        if (hub != null) {
            if (hub != lastHub) {
                getComponent().update(hub.getProcedures());
            }
        } else if (hubType != null) {
            if (hubType != lastHubType) {
                getComponent().update(hubType.getProcedures());
            }
        } else {
            if (lastHub != null || lastHubType != null) {
                getComponent().update(new ArrayList<Procedure>());
            }
        }
        lastHub = hub;
        lastHubType = hubType;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(HUB_PROCEDURES, getComponent().getProcedures());
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getProcedures().isEmpty()) {
            throw new WizardValidationException(null, null, LIMOResourceBundle.getString("VALUE_NOT_SET2", LIMOResourceBundle.getString("PROCEDURES")));
        }
    }

}
