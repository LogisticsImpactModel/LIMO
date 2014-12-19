package nl.fontys.sofa.limo.view.wizard.leg.scheduled;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.leg.multimode.MultimodeLegTablePanel;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;

/**
 * Panel to create ScheduledLeg
 *
 * @author Pascal Lindner
 */
public final class ScheduledLegPanel extends JPanel {

    private JPanel panelCenter, panelRight;
    private JButton btnAdd, btnEdit, btnDelete, btnAddAlt;
    private JTable table;
    private JLabel lblAlt, lblExpected, lblWaiting, lblAltName, lblMessage;
    private JTextField tfExpected, tfWaiting;
    private DefaultTableModel model;
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
        panelRight = new JPanel(new BorderLayout());
        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnEdit = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.EDIT)));
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        table = new JTable();
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
        model = new DefaultTableModel();
        model.addColumn("Acceptance Times");
        Box bv = Box.createVerticalBox();
        table = new JTable(model);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        JScrollPane sc = new JScrollPane(table);
        sc.setPreferredSize(new Dimension(160, 0));
        panelRight.add(sc, BorderLayout.CENTER);
        setActionListener();
        bv.add(btnAdd);
        bv.add(btnEdit);
        bv.add(btnDelete);
        panelRight.add(bv, BorderLayout.EAST);
        add(panelRight, BorderLayout.EAST);

    }

    //Return leg
    public ScheduledLeg getSchedueldLeg() {
        ScheduledLeg leg = new ScheduledLeg();
        try {
            leg.setExpectedTime(Long.parseLong(tfExpected.getText().replace(",", ".")));
            leg.setWaitingTimeLimit(Long.parseLong(tfWaiting.getText().replace(",", ".")));
            List<Long> times = new ArrayList<>();
            for (int i = 0; i < table.getRowCount(); i++) {
                times.add(Long.parseLong(table.getValueAt(i, 0).toString()));

            }
            if (times.size() > 0) {
                leg.setAcceptanceTimes(times);
            }
            if (altLeg != null) {
                leg.setAlternative(altLeg);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(ScheduledLegPanel.this,
                    LIMOResourceBundle.getString("NOT_A_NUMBER"),
                    LIMOResourceBundle.getString("NUMBER_ERROR"),
                    JOptionPane.ERROR_MESSAGE);
            leg.setExpectedTime(0);
            leg.setWaitingTimeLimit(0);
            tfExpected.setText("0");
            tfWaiting.setText("0");
        }
        return leg;
    }

    //AddActionListener
    public void setActionListener() {
        btnAddAlt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (btnAddAlt.getText().equals(LIMOResourceBundle.getString("ADD"))) {
                    NormalLegWizardAction wiz = new NormalLegWizardAction(new MultimodeLegTablePanel.FinishedLegListener() {

                        @Override
                        public void finishedLeg(Leg leg) {
                            altLeg = leg;
                            lblAltName.setText(leg.getName());
                            btnAddAlt.setText(LIMOResourceBundle.getString("EDIT"));
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

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long aTime = 0;

                String time = JOptionPane.showInputDialog(ScheduledLegPanel.this,
                        LIMOResourceBundle.getString("ACCEPTANCE_TIME"), null);
                if (time != null) {
                    if (!time.isEmpty()) {
                        try {
                            aTime = Long.parseLong(time.replace(",", "."));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(ScheduledLegPanel.this,
                                    LIMOResourceBundle.getString("NOT_A_NUMBER"),
                                    LIMOResourceBundle.getString("NUMBER_ERROR"),
                                    JOptionPane.ERROR_MESSAGE);
                        }
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
                    String time = JOptionPane.showInputDialog(ScheduledLegPanel.this,
                            LIMOResourceBundle.getString("ACCEPTANCE_TIME"), model.getValueAt(table.getSelectedRow(), 0));
                    if (time != null) {
                        if (!time.isEmpty()) {
                            try {
                                aTime = Long.parseLong(time.replace(",", "."));
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(ScheduledLegPanel.this,
                                        LIMOResourceBundle.getString("NOT_A_NUMBER"),
                                        LIMOResourceBundle.getString("NUMBER_ERROR"),
                                        JOptionPane.ERROR_MESSAGE);
                            }
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
