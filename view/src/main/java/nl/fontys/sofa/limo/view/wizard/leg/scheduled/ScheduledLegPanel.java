package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.custom.panel.AcceptanceTimePanel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;

/**
 * Panel to create ScheduledLeg
 *
 * @author Pascal Lindner
 */
public final class ScheduledLegPanel extends JPanel {

    private JPanel panelCenter;
    private AcceptanceTimePanel acceptancePanel;
    private JButton btnAddAlt;
    private JLabel lblAlt, lblExpected, lblWaiting, lblAltName, lblMessage;
    private JTextField tfExpected, tfWaiting;
    private Leg altLeg;

    public ScheduledLegPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("SCHEDULE");
    }

    private void initComponents() {
        panelCenter = new JPanel();
        acceptancePanel = new AcceptanceTimePanel();

        lblAlt = new JLabel(LIMOResourceBundle.getString("ALTERNATIVE"));
        lblMessage = new JLabel(LIMOResourceBundle.getString("VALUES_IN_MINUTES"));
        lblAltName = new JLabel("");
        lblExpected = new JLabel(LIMOResourceBundle.getString("EXPECTED_TIME"));
        lblWaiting = new JLabel(LIMOResourceBundle.getString("WAITING_TIME"));
        tfExpected = new JTextField();
        tfExpected.setText("0");
        tfExpected.setMinimumSize(new Dimension(60, tfExpected.getHeight()));
        tfWaiting = new JTextField();
        tfWaiting.setText("0");
        btnAddAlt = new JButton(LIMOResourceBundle.getString("ADD"));
        setLayout(new BorderLayout());
        setLayoutConstraints();
        add(panelCenter, BorderLayout.CENTER);
        setActionListener();
        add(acceptancePanel, BorderLayout.EAST);
    }

    public void update(long expectedTime, long waitingTime, List<Long> acceptanceTimes, Leg alternativeLeg) {
        tfExpected.setText("" + expectedTime);
        tfWaiting.setText("" + waitingTime);
        altLeg = alternativeLeg;

        remove(acceptancePanel);
        acceptancePanel = new AcceptanceTimePanel(acceptanceTimes);
        add(acceptancePanel, BorderLayout.EAST);

        if (altLeg != null) {
            lblAltName.setText(altLeg.getName());
            btnAddAlt.setText(LIMOResourceBundle.getString("EDIT"));
        }
    }

    public ScheduledLeg getSchedueldLeg() {
        ScheduledLeg leg = new ScheduledLeg();
        leg.setExpectedTime(Long.parseLong(tfExpected.getText().replace(",", ".")));
        leg.setWaitingTimeLimit(Long.parseLong(tfWaiting.getText().replace(",", ".")));
        List<Long> times = acceptancePanel.getAcceptanceTimes();

        if (times.size() > 0) {
            leg.setAcceptanceTimes(times);
        }
        if (altLeg != null) {
            leg.setAlternative(altLeg);
        }

        return leg;
    }

    public long getExpcetedTime() {
        return Long.parseLong(tfExpected.getText().replace(",", "."));
    }

    public long getWaitingTimeLimit() {
        return Long.parseLong(tfWaiting.getText().replace(",", "."));
    }

    public List<Long> getAcceptanceTimes() {
        return acceptancePanel.getAcceptanceTimes();
    }

    public Leg getAlternativeLeg() {
        return altLeg;
    }

    public void setActionListener() {
        btnAddAlt.addActionListener((ActionEvent e) -> {
            NormalLegWizardAction wiz = new NormalLegWizardAction((Leg leg) -> {
                altLeg = leg;
                lblAltName.setText(leg.getName());
                btnAddAlt.setText(LIMOResourceBundle.getString("EDIT"));
            });
            if (altLeg != null) {
                wiz.setUpdate(altLeg);
            }
            wiz.actionPerformed(e);
        });

    }

    //SetLayoutConstraints
    public void setLayoutConstraints() {
        panelCenter.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        panelCenter.add(lblMessage, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        panelCenter.add(lblExpected, c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        panelCenter.add(tfExpected, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        panelCenter.add(lblWaiting, c);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        panelCenter.add(tfWaiting, c);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        panelCenter.add(lblAlt, c);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 4;
        panelCenter.add(lblAltName, c);
        c.gridwidth = 2;
        c.gridx = 1;
        c.gridy = 3;

        panelCenter.add(btnAddAlt, c);
    }
}
