/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegTablePanel;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegWizardAction;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;
import nl.fontys.sofa.limo.view.wizard.leg.scheduled.ScheduledLegWizardAction;

/**
 *
 * @author lnx
 */
public class SelectLegTypePanel extends JPanel {

    public SelectLegTypePanel() {
        init();
    }

    private void init() {
        JFrame frame = new JFrame();
        JButton btnNormal = new JButton("Normal Leg");
        JButton btnMulti = new JButton("Multimode Leg");
        JButton btnSchedule = new JButton("Scheduled Leg");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        btnNormal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NormalLegWizardAction wiz = new NormalLegWizardAction(new MultimodeLegTablePanel.FinishedLegListener() {

                    @Override
                    public void finishedLeg(Leg leg) {
                        //here your leg
                    }
                });
                wiz.actionPerformed(e);
            }
        });
        btnMulti.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MultimodeLegWizardAction wiz = new MultimodeLegWizardAction(new MultimodeLegTablePanel.FinishedMapListener() {

                    @Override
                    public void finishedLeg(Map map) {
                        //map of legs
                    }
                });
                wiz.actionPerformed(e);
            }
        });
        btnSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScheduledLegWizardAction wiz = new ScheduledLegWizardAction(new MultimodeLegTablePanel.FinishedScheduledLegListener() {

                    @Override
                    public void finishedLeg(ScheduledLeg leg) {
                        //Schedueld leg
                    }
                });
                wiz.actionPerformed(e);
            }
        });
        panel.add(btnNormal);
        panel.add(btnMulti);
        panel.add(btnSchedule);

        Object[] options = {"Cancel"};
        int n = JOptionPane.showOptionDialog(frame,
                panel,
                "Select a Leg",
                JOptionPane.CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
    }
}
