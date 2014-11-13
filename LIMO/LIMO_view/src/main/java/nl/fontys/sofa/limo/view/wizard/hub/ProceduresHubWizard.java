package nl.fontys.sofa.limo.view.wizard.hub;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.pane.ProceduresPanel;
import static nl.fontys.sofa.limo.view.wizard.hub.HubWizardAction.*;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class ProceduresHubWizard implements WizardDescriptor.Panel<WizardDescriptor> {

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
        Hub hub = (Hub) wiz.getProperty(HUB_COPY);
        if (hub != null) {
            getComponent().update(hub.getProcedures());
        } else {
            HubType htyp = (HubType) wiz.getProperty(HUB_TYPE);
            if (htyp != null) {
                getComponent().update(htyp.getProcedures());
            }
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(HUB_PROCEDURES, getComponent().getProcedures());
    }

}
