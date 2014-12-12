/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.types.hub;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.type.HubType;
import static nl.fontys.sofa.limo.view.wizard.types.TypeWizardAction.TYPE_EVENT;
import nl.fontys.sofa.limo.view.wizard.types.leg.LegTypeWizardAction;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class EventHubTypeWizard implements WizardDescriptor.Panel<WizardDescriptor> {

    private EventHubTypePanel component;
    private HubType lastType;

    @Override
    public EventHubTypePanel getComponent() {
        if (component == null) {
            component = new EventHubTypePanel();
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
        HubType hubType = (HubType) wiz.getProperty(LegTypeWizardAction.TYPE_OLDTYPE);
        if (hubType != null) {
            if (hubType != lastType) {
                getComponent().update(hubType.getEvents());
            }
        } else {
            if (lastType != null) {
                getComponent().update(new ArrayList<Event>());
            }
        }
        lastType = hubType;
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        wiz.putProperty(TYPE_EVENT, component.getEvents());
    }
}