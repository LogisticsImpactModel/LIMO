/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.types.leg;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.type.LegType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_EVENT;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class EventLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventLegTypePanel component;

    @Override
    public EventLegTypePanel getComponent() {
        if (component == null) {
            component = new EventLegTypePanel();
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
        LegType lt = (LegType) wiz.getProperty(TYPE_EVENT);
        if (lt != null) {
            getComponent().update(lt.getEvents());
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(TYPE_EVENT, component.getEvents());
    }

}
