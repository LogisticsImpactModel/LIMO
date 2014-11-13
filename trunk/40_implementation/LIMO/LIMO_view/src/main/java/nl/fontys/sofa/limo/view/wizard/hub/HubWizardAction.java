/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.hub;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.api.service.provider.HubService;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.hub.Hub;
import nl.fontys.sofa.limo.domain.component.hub.Location;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:
@ActionID(category = "Hub", id = "nl.fontys.limo.view.wizzard.hub.HubWizardAction")
@ActionRegistration(displayName = "Add Hub")
@ActionReference(path = "Menu/Data/Hub", position = 20)
public final class HubWizardAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<>();
        if (!update) {
            panels.add(new HubWizardPanel1());
        }
        panels.add(new HubWizardPanel2());
        panels.add(new HubWizardPanel3());
        panels.add(new HubWizardPanel5());
        panels.add(new HubWizardPanel4());
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }

        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<>(panels));

        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Add Hub");
        if (update) {
            wiz.putProperty("hubCopy", hubUpdate);

        }

        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {

            HubService service = Lookup.getDefault().lookup(HubService.class);
            //    Hub hub = (Hub) wiz.getProperty("hub");
            if (update) {
                hubUpdate.setName((String) wiz.getProperty("name"));
                hubUpdate.setDescription((String) wiz.getProperty("description"));
                hubUpdate.setIcon((Icon) wiz.getProperty("icon"));
                hubUpdate.setLocation((Location) wiz.getProperty("location"));
                hubUpdate.setProcedures((List<Procedure>) wiz.getProperty("procedures"));
                hubUpdate.setEvents((List<Event>) wiz.getProperty("events"));
                    //  if (hub.getId() == null) {
                //      hub.setId(hubUpdate.getId());
                //   }
                service.update(hubUpdate);
            } else {
                Hub hub = new Hub();
                hub.setName((String) wiz.getProperty("name"));
                hub.setDescription((String) wiz.getProperty("description"));
                hub.setIcon((Icon) wiz.getProperty("icon"));
                hub.setLocation((Location) wiz.getProperty("location"));
                hub.setProcedures((List<Procedure>) wiz.getProperty("procedures"));
                hub.setEvents((List<Event>) wiz.getProperty("events"));
                service.insert(hub);

            }
        }

    }

    public void isUpdate(boolean update, Hub hubUpdate) {
        this.update = update;
        this.hubUpdate = hubUpdate;
    }

    private boolean update = false;
    private Hub hubUpdate;
}
