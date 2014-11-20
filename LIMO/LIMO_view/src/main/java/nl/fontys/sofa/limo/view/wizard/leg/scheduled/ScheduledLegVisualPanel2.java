package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegTablePanel;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;

public final class ScheduledLegVisualPanel2 extends JPanel {

    private JPanel panelCenter, panelRight;
    private JButton btnAdd, btnEdit, btnDelete, btnAddAlt;
    private JTable table;
    private JLabel lblAlt, lblExpected, lblWaiting, lblAltName, lblMessage;
    private JTextField tfExpected, tfWaiting;
    private DefaultTableModel model;
    private Leg altLeg;

    /**
     * Creates new form ScheduledLegVisualPanel2
     */
    public ScheduledLegVisualPanel2() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Schedule";
    }

    private void initComponents() {
        panelCenter = new JPanel();
        panelRight = new JPanel(new BorderLayout());
        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnEdit = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.EDIT)));
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        table = new JTable();
        lblAlt = new JLabel("Alternative");
        lblMessage = new JLabel("All values are in minutes!");
        lblAltName = new JLabel("");
        lblExpected = new JLabel("Expected time");
        lblWaiting = new JLabel("Waiting time");
        tfExpected = new JTextField();
        tfExpected.setText("0");
        tfExpected.setMinimumSize(new Dimension(60, tfExpected.getHeight()));
        tfWaiting = new JTextField();
        tfWaiting.setText("0");
        btnAddAlt = new JButton("Add");
        setLayout(new BorderLayout());
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
        btnAddAlt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (btnAddAlt.getText().equals("Add")) {
                    NormalLegWizardAction wiz = new NormalLegWizardAction(new MultimodeLegTablePanel.FinishedLegListener() {

                        @Override
                        public void finishedLeg(Leg leg) {
                            altLeg = leg;
                            lblAltName.setText(leg.getName());
                            btnAddAlt.setText("Edit");
                        }
                    });
                    wiz.actionPerformed(e);
                } else {
                    NormalLegWizardAction wiz = new NormalLegWizardAction(new MultimodeLegTablePanel.FinishedLegListener() {

                        @Override
                        public void finishedLeg(Leg leg) {
                            altLeg = leg;
                            lblAltName.setText(leg.getName());
                        }
                    });
                    wiz.update(altLeg);
                    wiz.actionPerformed(e);
                }
            }
        });
        panelCenter.add(btnAddAlt, c);

        add(panelCenter, BorderLayout.CENTER);
        model = new DefaultTableModel();
        model.addColumn("Acceptance Times");

        Box bv = Box.createVerticalBox();
        table = new JTable(model);
        JScrollPane sc = new JScrollPane(table);
        sc.setPreferredSize(new Dimension(160, 0));
        panelRight.add(sc, BorderLayout.CENTER);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long aTime = 0;

                String time = JOptionPane.showInputDialog(ScheduledLegVisualPanel2.this,
                        "Acceptance Time", null);
                if (time != null) {
                    if (!time.isEmpty()) {
                        aTime = Long.parseLong(time.replace(",", "."));
                    }
                    model.addRow(new Long[]{aTime});
                }
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    long aTime = 0;
                    String time = JOptionPane.showInputDialog(ScheduledLegVisualPanel2.this,
                            "Acceptance Time", model.getValueAt(table.getSelectedRow(), 0));
                    if (time != null) {
                        if (!time.isEmpty()) {
                            aTime = Long.parseLong(time.replace(",", "."));
                        }
                        model.setValueAt(aTime, table.getSelectedRow(), 0);
                    }
                }
            }
        });

        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    model.removeRow(table.getSelectedRow());
                }
            }
        });

        bv.add(btnAdd);
        bv.add(btnEdit);
        bv.add(btnDelete);

        panelRight.add(bv, BorderLayout.EAST);
        add(panelRight, BorderLayout.EAST);

    }
    
    public ScheduledLeg getSchedueldLeg(){
        ScheduledLeg leg = new ScheduledLeg();
        leg.setExpectedTime(Long.parseLong(tfExpected.getText().replace(",", ".")));
        leg.setWaitingTimeLimit(Long.parseLong(tfWaiting.getText().replace(",", ".")));
        return leg;
    }

}
