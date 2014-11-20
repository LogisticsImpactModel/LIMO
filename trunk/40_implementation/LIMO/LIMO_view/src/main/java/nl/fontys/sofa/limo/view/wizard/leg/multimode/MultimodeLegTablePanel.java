package nl.fontys.sofa.limo.view.wizard.leg.multimode;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.domain.component.leg.ScheduledLeg;
import nl.fontys.sofa.limo.view.util.IconUtil;
import nl.fontys.sofa.limo.view.wizard.leg.normal.NormalLegWizardAction;

public final class MultimodeLegTablePanel extends JPanel {

    private JTable table;
    private MultimodeLegModel model;
    // private JPanel panel;
    private JButton btnAdd, btnEdit, btnDelete;

    public MultimodeLegTablePanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Multimode Leg";
    }

    private void initComponents() {
        model = new MultimodeLegModel();
        table = new JTable(model);
        setLayout(new BorderLayout());
        add(table, BorderLayout.CENTER);
        //   panel = new JPanel();
        Box bv = Box.createVerticalBox();
        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NormalLegWizardAction wiz = new NormalLegWizardAction(new FinishedLegListener() {

                    @Override
                    public void finishedLeg(Leg leg) {
                        String prop = JOptionPane.showInputDialog(MultimodeLegTablePanel.this,
                                "Propability in %", null);
                        double propability = 0.0;
                        if (prop != null) {
                            try {
                                propability = Double.parseDouble(prop.replace(",", "."));
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(MultimodeLegTablePanel.this,
                                        "Not a number!",
                                        "Number error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        model.addLeg(leg, propability);

                    }
                });
                wiz.actionPerformed(e);
            }
        });
        btnEdit = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.EDIT)));

        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                NormalLegWizardAction wiz = new NormalLegWizardAction(new FinishedLegListener() {

                    @Override
                    public void finishedLeg(Leg leg) {
                        if (table.getSelectedRow() >= 0) {
                            String prop = JOptionPane.showInputDialog(MultimodeLegTablePanel.this,
                                    "Propability", model.getPropability(table.getSelectedRow()));
                            double propability = 0.0;

                            if (prop != null) {
                                try {
                                    propability = Double.parseDouble(prop.replace(",", "."));
                                } catch (NumberFormatException e) {
                                    JOptionPane.showMessageDialog(MultimodeLegTablePanel.this,
                                            "Not a number!",
                                            "Number error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                propability = model.getPropability(table.getSelectedRow());
                            }
                            model.updateLeg(leg, propability, table.getSelectedRow());
                        }
                    }
                });
                wiz.update(model.getLeg(table.getSelectedRow()));
                wiz.actionPerformed(e);
            }
        });
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));
        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() >= 0) {
                    model.removeLeg(table.getSelectedRow());
                }
            }
        });
        bv.add(btnAdd);
        bv.add(btnEdit);
        bv.add(btnDelete);
        add(bv, BorderLayout.EAST);

    }

    public Map<Leg, Double> getMap() {
        return model.getMap();
    }

    public class MultimodeLegModel extends AbstractTableModel {

        Map<Leg, Double> legMap;
        List<Leg> legList;

        public MultimodeLegModel() {
            legMap = new HashMap<>();
            legList = new ArrayList<>();
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
            if (col == 1) {
                return true;
            }
            return false;
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
                        "Not a number!",
                        "Number error",
                        JOptionPane.ERROR_MESSAGE);
                fireTableDataChanged();
            }
        }

    }

    public interface FinishedLegListener {

        public void finishedLeg(Leg leg);
    }

    public interface FinishedMapListener {

        public void finishedLeg(Map map);
    }

    public interface FinishedScheduledLegListener {

        public void finishedLeg(ScheduledLeg leg);
    }
}
