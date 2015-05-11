
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.custom.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * The AcceptanceTimesPropertyEditor class provides the acceptance times in a
 * list. Beyound that, it provides the option to edit, delete and add acceptance
 * times.
 *
 * @author Christina Zenzes
 */
public class AcceptanceTimePanel extends JPanel {

    private JPanel panelRight;
    private JButton btnEdit;
    private JTable table;
    private JButton btnDelete;
    protected DefaultTableModel model;
    private JButton btnAdd;

    public AcceptanceTimePanel(List<Long> acceptanceTime) {
        this();
        for (Long time : acceptanceTime) {
            model.addRow(new Long[]{time});
        }
    }

    public AcceptanceTimePanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("ACCEPTANCE_TIME");
    }

    private void initComponents() {

        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnEdit = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.EDIT)));
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        table = new JTable();
        setLayout(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn(LIMOResourceBundle.getString("ACCEPTANCE_TIME"));
        Box bv = Box.createVerticalBox();
        table = new JTable(model);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        JScrollPane sc = new JScrollPane(table);
        sc.setPreferredSize(new Dimension(160, 0));
        add(sc, BorderLayout.CENTER);
        setActionListener();
        bv.add(btnAdd);
        bv.add(btnEdit);
        bv.add(btnDelete);
        add(bv, BorderLayout.EAST);
    }

    public void setActionListener() {
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long aTime = 0;

                String time = JOptionPane.showInputDialog(AcceptanceTimePanel.this,
                        LIMOResourceBundle.getString("ACCEPTANCE_TIME"), null);
                if (time != null) {
                    if (!time.isEmpty()) {
                        try {
                            aTime = Long.parseLong(time.replace(",", "."));
                            model.addRow(new Long[]{aTime});
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(AcceptanceTimePanel.this,
                                    LIMOResourceBundle.getString("NOT_A_NUMBER"),
                                    LIMOResourceBundle.getString("NUMBER_ERROR"),
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    long aTime = 0;
                    String time = JOptionPane.showInputDialog(AcceptanceTimePanel.this,
                            LIMOResourceBundle.getString("ACCEPTANCE_TIME"), model.getValueAt(table.getSelectedRow(), 0));
                    if (time != null) {
                        if (!time.isEmpty()) {
                            try {
                                aTime = Long.parseLong(time.replace(",", "."));
                                model.setValueAt(aTime, table.getSelectedRow(), 0);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(AcceptanceTimePanel.this,
                                        LIMOResourceBundle.getString("NOT_A_NUMBER"),
                                        LIMOResourceBundle.getString("NUMBER_ERROR"),
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }

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

    protected JTable getTable() {
        return table;
    }

    /**
     * retunring all accaptance times which are shown in the panel
     *
     * @return {@code List<Long>} wiht longs which represents acceptance times
     */
    public List<Long> getAcceptanceTimes() {
        List<Long> times = new ArrayList<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            times.add(Long.parseLong(table.getValueAt(i, 0).toString()));

        }
        return times;
    }

}
