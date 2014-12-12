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
 *
 * @author lnx
 */
public class SelectLegTypePanel extends JPanel {

    private static JOptionPane optionPane;
    private Leg leg;

    public SelectLegTypePanel() {
        init();
    }

    private void init() {
        JButton btnNormal = new JButton(LIMOResourceBundle.getString("NORMAL_LEG"));
        JButton btnMulti = new JButton(LIMOResourceBundle.getString("MULTIMODE_LEG"));
        JButton btnSchedule = new JButton(LIMOResourceBundle.getString("SCHEDULED_LEG"));
        optionPane = new JOptionPane();

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        btnNormal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NormalLegWizardAction wiz = new NormalLegWizardAction(new MultimodeLegTablePanel.FinishedLegListener() {

                    @Override
                    public void finishedLeg(Leg leg) {
                        SelectLegTypePanel.this.setLeg(leg);
                    }
                });
                wiz.actionPerformed(e);
                JOptionPane.getRootFrame().dispose();
            }
        });
        btnMulti.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                MultimodeLegWizardAction wiz = new MultimodeLegWizardAction(new MultimodeLegTablePanel.FinishedMapListener() {

                    @Override
                    public void finishedLeg(Map map) {
                        MultiModeLeg leg = new MultiModeLeg();
                        leg.setLegs(map);
                        SelectLegTypePanel.this.setLeg(leg);
                    }
                });
                wiz.actionPerformed(e);
                JOptionPane.getRootFrame().dispose();
            }
        });
        btnSchedule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ScheduledLegWizardAction wiz = new ScheduledLegWizardAction(new MultimodeLegTablePanel.FinishedScheduledLegListener() {

                    @Override
                    public void finishedLeg(ScheduledLeg leg) {
                        SelectLegTypePanel.this.setLeg(leg);
                    }
                });
                wiz.actionPerformed(e);
                JOptionPane.getRootFrame().dispose();
            }
        });
        panel.add(btnNormal);
        panel.add(btnMulti);
        panel.add(btnSchedule);

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
}
