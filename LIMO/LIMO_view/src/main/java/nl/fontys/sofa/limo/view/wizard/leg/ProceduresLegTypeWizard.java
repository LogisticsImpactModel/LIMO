/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.leg;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import nl.fontys.sofa.limo.view.custom.pane.ProceduresPanel;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class ProceduresLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor>, WizardDescriptor.ValidatingPanel<WizardDescriptor> {

    private ProceduresPanel component;
    private final ResourceBundle bundle;

    public ProceduresLegTypeWizard() {
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

            HubType htyp = (HubType) wiz.getProperty("legTypeCopy");
            if (htyp != null) {
                getComponent().update(htyp.getProcedures());
            }
        }
    

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("procedures", getComponent().getProcedures());
    }

    @Override
    public void validate() throws WizardValidationException {
        if (component.getProcedures().isEmpty()) {
            throw new WizardValidationException(null, null, MessageFormat.format(bundle.getString("VALUE_NOT_SET2"), bundle.getString("PROCEDURES")));
        }
    }

}
