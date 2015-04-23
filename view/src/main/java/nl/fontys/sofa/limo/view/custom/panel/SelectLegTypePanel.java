package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegTablePanel;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegWizardAction;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;
import nl.fontys.sofa.limo.view.wizard.leg.scheduled.ScheduledLegWizardAction;

/**
 * Panel to select what kind of leg should be created.
 *
 * @author Pascal Lindner
 */
public class SelectLegTypePanel extends JPanel {

    private Leg leg;
    private JButton btnNormal, btnMulti, btnSchedule;

    public SelectLegTypePanel() {
        init();
    }

    private void init() {
        btnNormal = new JButton(LIMOResourceBundle.getString("NORMAL_LEG"));
        btnMulti = new JButton(LIMOResourceBundle.getString("MULTIMODE_LEG"));
        btnSchedule = new JButton(LIMOResourceBundle.getString("SCHEDULED_LEG"));
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(btnNormal);
        panel.add(btnMulti);
        panel.add(btnSchedule);
        setButtonListener();
        Object[] options = {LIMOResourceBundle.getString("CANCEL")};
        int result = JOptionPane.showOptionDialog(this,
                panel,
                LIMOResourceBundle.getString("SELECT_LEG"),
                JOptionPane.CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
        if (result == 0) {
            JOptionPane.getRootFrame().dispose();
        }
    }

    public void setLeg(Leg leg) {
        this.leg = leg;
    }

    public Leg getLeg() {
        return leg;
    }

    public void setButtonListener() {
        btnNormal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Call the wizard
                NormalLegWizardAction wiz = new NormalLegWizardAction(new MultimodeLegTablePanel.FinishedLegListener() {
                    //Get the Leg
                    @Override
                    public void finishedLeg(Leg leg) {
                        SelectLegTypePanel.this.setLeg(leg);
                    }
                });
                JOptionPane.getRootFrame().dispose();
                wiz.actionPerformed(e);

            }
        });
        btnMulti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MultimodeLegWizardAction wiz = new MultimodeLegWizardAction(new MultimodeLegTablePanel.FinishedMultiModeLegListener() {
                    //Get the Leg
                    @Override
                    public void finishedLeg(MultiModeLeg leg) {
                        SelectLegTypePanel.this.setLeg(leg);
                    }
                });
                JOptionPane.getRootFrame().dispose();
                wiz.actionPerformed(e);

            }
        });
        btnSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Call the wizard
                ScheduledLegWizardAction wiz = new ScheduledLegWizardAction(new MultimodeLegTablePanel.FinishedScheduledLegListener() {
                    //Get the Leg
                    @Override
                    public void finishedLeg(ScheduledLeg leg) {
                        SelectLegTypePanel.this.setLeg(leg);
                    }
                });
                JOptionPane.getRootFrame().dispose();
                wiz.actionPerformed(e);

            }
        });
    }
}
