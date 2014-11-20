/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegTablePanel;
import nl.fontys.sofa.limo.view.wizard.leg.normal.EventLegTypeWizard;
import nl.fontys.sofa.limo.view.wizard.leg.normal.ProceduresLegTypeWizard;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:
//@ActionID(category = "Leg", id = "nl.fontys.limo.view.wizzard.leg.scheduled.ScheduledLegWizardAction")
//@ActionRegistration(displayName = "Add Scheduled leg")
//@ActionReference(path = "Menu/Master Data/Leg", position = 20)
public final class ScheduledLegWizardAction implements ActionListener {
        
//    public ScheduledLegWizardAction(){
//        legListener = new MultimodeLegTablePanel.FinishedScheduledLegListener() {
//
//            @Override
//            public void finishedLeg(ScheduledLeg leg) {
//                
//            }
//        };
//    }
        public ScheduledLegWizardAction(MultimodeLegTablePanel.FinishedScheduledLegListener legListener) {
        this.legListener = legListener;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        panels.add(new NameDescriptionIconLegPanel());
        panels.add(new ScheduledLegScheduleWizard());
         panels.add(new EventLegTypeWizard());
        panels.add(new ProceduresLegTypeWizard());
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
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<WizardDescriptor>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Scheduled Leg");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            ScheduledLeg leg = (ScheduledLeg) wiz.getProperty("leg");
            leg.setEvents((List<Event>) wiz.getProperty("events"));
                        leg.setProcedures((List<Procedure>) wiz.getProperty("procedures"));

            legListener.finishedLeg(leg);
        }
    }
    
        private final MultimodeLegTablePanel.FinishedScheduledLegListener legListener;


}
