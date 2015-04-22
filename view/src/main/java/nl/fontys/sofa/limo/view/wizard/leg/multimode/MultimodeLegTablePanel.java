package nl.fontys.sofa.limo.view.wizard.leg.multimode;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.MultiModeLeg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;

/**
 * Table to Handle Multimode Legs
 *
 * @author Pascal Lindner
 */
public class MultimodeLegTablePanel extends JPanel {

    private JTable table;
    private MultimodeLegModel model;
    private JButton btnAdd, btnEdit, btnDelete;

    public MultimodeLegTablePanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("MULTIMODE_LEG");
    }

    private void initComponents() {
        model = new MultimodeLegModel();
        table = new JTable(model);
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        Box bv = Box.createVerticalBox();
        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnEdit = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.EDIT)));
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
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
                NormalLegWizardAction wiz = new NormalLegWizardAction(new FinishedLegListener() {

                    @Override
                    public void finishedLeg(Leg leg) {
                        String prop = JOptionPane.showInputDialog(MultimodeLegTablePanel.this,
                                LIMOResourceBundle.getString("WEIGHT"), null);
                        double propability = 0.0;
                        if (prop != null) {
                            try {
                                propability = Double.parseDouble(prop.replace(",", "."));
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(MultimodeLegTablePanel.this,
                                        LIMOResourceBundle.getString("NOT_A_NUMBER"),
                                        LIMOResourceBundle.getString("NUMBER_ERROR"),
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        model.addLeg(leg, propability);
                        firePropertyChange("leg", null, leg);
                    }
                });
                wiz.actionPerformed(e);
            }
        });

        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NormalLegWizardAction wiz = new NormalLegWizardAction(new FinishedLegListener() {

                    @Override
                    public void finishedLeg(Leg leg) {
                        if (table.getSelectedRow() >= 0) {
                            String prop = JOptionPane.showInputDialog(MultimodeLegTablePanel.this,
                                    LIMOResourceBundle.getString("WEIGHT"), model.getPropability(table.getSelectedRow()));
                            double propability = 0.0;

                            if (prop != null) {
                                try {
                                    propability = Double.parseDouble(prop.replace(",", "."));
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(MultimodeLegTablePanel.this,
                                            LIMOResourceBundle.getString("NOT_A_NUMBER"),
                                            LIMOResourceBundle.getString("NUMBER_ERROR"),
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                propability = model.getPropability(table.getSelectedRow());
                            }
                            model.updateLeg(leg, propability, table.getSelectedRow());
                            firePropertyChange("leg", null, leg);

                        }
                    }
                });
                wiz.setUpdate(model.getLeg(table.getSelectedRow()));
                wiz.actionPerformed(e);
            }
        });
        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    model.removeLeg(table.getSelectedRow());
                    firePropertyChange("leg", null, null);
                }
            }
        });
    }

    public MultimodeLegModel getLegModel() {
        return model;
    }

    /**
     * Table Model for Multimode legs
     *
     * @author Pascal Lindner
     */
    public class MultimodeLegModel extends AbstractTableModel {

        Map<Leg, Double> legMap;
        List<Leg> legList;

        public MultimodeLegModel() {
            legMap = new HashMap<>();
            legList = new ArrayList<>();
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return LIMOResourceBundle.getString("LEG");
                case 1:
                    return LIMOResourceBundle.getString("WEIGHT");
                default:
                    return "";
            }
        }

        @Override
        public int getRowCount() {
            return legMap.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return legList.get(rowIndex).getName();
            } else {
                return legMap.get(legList.get(rowIndex));
            }
        }

        public void addLeg(Leg leg, double prop) {
            legMap.put(leg, prop);
            legList.add(leg);
            fireTableDataChanged();
        }

        /**
         * Add multiple legs to the model
         *
         * @param legs The legs which should be added.
         */
        public void addLegs(Map<Leg, Double> legs) {
            if (legs != null) {
                for (Map.Entry<Leg, Double> entry : legs.entrySet()) {
                    addLeg(entry.getKey(), entry.getValue());
                }

                fireTableDataChanged();
            }
        }

        public Map<Leg, Double> getMap() {
            return legMap;
        }

        public Leg getLeg(int row) {
            return legList.get(row);
        }

        public void updateLeg(Leg leg, double propability, int row) {
            legMap.remove(legList.get(row));
            legMap.put(leg, propability);
            legList.remove(row);
            legList.add(row, leg);
            fireTableDataChanged();
        }

        public double getPropability(int row) {
            return legMap.get(legList.get(row));
        }

        public void removeLeg(int row) {
            legMap.remove(legList.get(row));
            legList.remove(row);
            fireTableDataChanged();
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return (col == 1);
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            double propability = 0.0;
            try {
                if (value.toString().contains(",")) {
                    propability = Double.parseDouble(value.toString().replace(",", "."));
                } else {
                    propability = Double.parseDouble(value.toString());
                }
                legMap.put(legList.get(row), propability);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MultimodeLegTablePanel.this,
                        LIMOResourceBundle.getString("NOT_A_NUMBER"),
                        LIMOResourceBundle.getString("NUMBER_ERROR"),
                        JOptionPane.ERROR_MESSAGE);
                fireTableDataChanged();
            }
        }
    }

    /**
     * Listener for creating legs
     *
     * @author Pascal Lindner
     */
    public interface FinishedLegListener {

        public void finishedLeg(Leg leg);
    }

    public interface FinishedMultiModeLegListener {

        public void finishedLeg(MultiModeLeg leg);
    }

    public interface FinishedScheduledLegListener {

        public void finishedLeg(ScheduledLeg leg);
    }
}
