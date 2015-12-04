/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.actions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.SupplyChain;
import nl.fontys.sofa.limo.simulation.Simulator;
import nl.fontys.sofa.limo.simulation.SimulatorTask;
import nl.fontys.sofa.limo.simulation.SimulatorTaskListener;
import nl.fontys.sofa.limo.view.topcomponent.ResultTopComponent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.Exceptions;
import org.openide.windows.WindowManager;

/**
 *
 * @author nilsh
 */
public class ProjectChainSimulateAction extends AbstractAction implements SimulatorTaskListener {

    private static int NUMBER_OF_RUNS = 100000;
    private SupplyChain chain;

    public ProjectChainSimulateAction(SupplyChain chain) {
        this.chain = chain;
        putValue(NAME, "Simulate Chain");
    }

    private int number;
    private DialogDescriptor dialog;

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        JTextField text = new JTextField(String.valueOf(NUMBER_OF_RUNS));
        JLabel label1 = new JLabel("Number of runs:");
        JLabel error = new JLabel(" ");
        error.setForeground(Color.red);
        p.add(label1);
        p.add(text);
        panel.add(p, BorderLayout.NORTH);
        panel.add(error, BorderLayout.SOUTH);
        dialog = new DialogDescriptor(panel, "Choose number of runs", true, (ActionEvent e1) -> {
            if (e1.getSource() == DialogDescriptor.CANCEL_OPTION) {
                dialog.setClosingOptions(null);
            } else if (e1.getSource() == DialogDescriptor.OK_OPTION) {
                String s = text.getText();
                try {
                    number = Integer.parseInt(s);
                    if (number <= 0) {
                        throw new NumberFormatException();
                    }
                    SupplyChain[] c = {chain};
                    SimulatorTask task = Simulator.simulate(number, c);
                    task.addTaskListener(ProjectChainSimulateAction.this);
                    dialog.setClosingOptions(null);
                } catch (NumberFormatException ex) {
                    error.setText("Please insert a number bigger 0");
                }
            }
        });
        dialog.setClosingOptions(new Object[]{});
        DialogDisplayer.getDefault().notifyLater(dialog);
        //      String userInput = input.getInputText();

    }

    @Override
    public void taskFinished(SimulatorTask task) {
        WindowManager.getDefault().invokeWhenUIReady(() -> {
            try {
                ResultTopComponent rtc = new ResultTopComponent(task.getResults());
                rtc.open();
                rtc.requestActive();
            } catch (InstantiationException ex) {
                Exceptions.printStackTrace(ex);
            }
        });

    }

}
