/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.leg.normal;

import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class EventLegTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventsLegTypePanel component;

    @Override
    public EventsLegTypePanel getComponent() {
        if (component == null) {
            component = new EventsLegTypePanel();
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
        Leg leg = (Leg) wiz.getProperty("leg");
        if (leg != null) {
            getComponent().update(leg);
        }
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty("events", component.getEvents());
    }

}
