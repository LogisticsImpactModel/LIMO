package nl.fontys.sofa.limo.view.custom;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.view.util.IconUtil;
import org.openide.util.Lookup;

public class TabbedComponentEntries<T extends Component> extends JPanel {

    private final String[] columnNames;

    private final JTabbedPane tabbedPane;
    private final ResourceBundle bundle;
    private final JPanel panelLeft;
    private final JTable costsTable, delaysTable, leadTimesTable;
    private final JScrollPane costsScrollPane, delaysScrollPane, leadTimesScrollPane;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;

    public TabbedComponentEntries(T component) {
        this.bundle = ResourceBundle.getBundle("nl.fontys.sofa.limo.view.custom.Bundle");
        this.columnNames = new String[]{
            bundle.getString("costs.name"),
            bundle.getString("costs.category"),
            bundle.getString("costs.value")
        };
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        costsTable = new JTable();
        delaysTable = new JTable();
        leadTimesTable = new JTable();
        costsScrollPane = new JScrollPane();
        delaysScrollPane = new JScrollPane();
        leadTimesScrollPane = new JScrollPane();
        panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));

        btnAdd = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.ADD)));
        btnEdit = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.EDIT)));
        btnDelete = new JButton(new ImageIcon(IconUtil.getIcon(IconUtil.UI_ICON.TRASH)));

        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addEntry(tabbedPane.getSelectedIndex());
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabbedPane.getSelectedIndex() == 0) {
                    EntryTableModel model = (EntryTableModel) costsTable.getModel();
                    if (costsTable.getSelectedRow() != -1) {
                        model.remove(costsTable.getSelectedRow());
                    }
                } else {
                    if (tabbedPane.getSelectedIndex() == 1) {
                        EntryTableModel model = (EntryTableModel) delaysTable.getModel();
                        if (delaysTable.getSelectedRow() != -1) {
                            model.remove(delaysTable.getSelectedRow());
                        }
                    } else {
                        if (tabbedPane.getSelectedIndex() == 2) {
                            EntryTableModel model = (EntryTableModel) leadTimesTable.getModel();
                            if (delaysTable.getSelectedRow() != -1) {
                                model.remove(delaysTable.getSelectedRow());
                            }
                        }
                    }
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateEntry(tabbedPane.getSelectedIndex(), costsTable.getSelectedRow());
            }
        });

        panelLeft.add(btnAdd);
        panelLeft.add(btnEdit);
        panelLeft.add(btnDelete);

        costsTable.setModel(new EntryTableModel(component.getCosts()));
        costsScrollPane.setViewportView(costsTable);
        tabbedPane.addTab(bundle.getString("costs"), costsScrollPane);

        delaysTable.setModel(new EntryTableModel(component.getDelays()));
        delaysScrollPane.setViewportView(delaysTable);
        tabbedPane.addTab(bundle.getString("delays"), delaysScrollPane);

        leadTimesTable.setModel(new EntryTableModel(component.getLeadTimes()));
        leadTimesScrollPane.setViewportView(leadTimesTable);
        tabbedPane.addTab(bundle.getString("lead_times"), leadTimesScrollPane);

        /*GroupLayout layout = new GroupLayout(this);
         this.setLayout(layout);
         layout.setHorizontalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
         .addContainerGap()
         .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
         .addContainerGap())
         );
         layout.setVerticalGroup(
         layout.createParallelGroup(GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
         .addContainerGap()
         .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
         .addContainerGap())
         );*/
        BorderLayout layout = new BorderLayout();
        this.setLayout(layout);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.add(panelLeft, BorderLayout.EAST);

        //this.add(editOptionsPanel, BorderLayout.EAST);
    }

    public List<Entry> getCosts() {
        EntryTableModel model = (EntryTableModel) costsTable.getModel();
        return model.getList();
    }

    public List<Entry> getDelays() {
        EntryTableModel model = (EntryTableModel) delaysTable.getModel();
        return model.getList();
    }

    public List<Entry> getLeads() {
        EntryTableModel model = (EntryTableModel) leadTimesTable.getModel();
        return model.getList();
    }

    public void updateEntry(int table, int rowIndex) {

        EntryPanel panel;
        if (table == 0) {
            EntryTableModel model = (EntryTableModel) costsTable.getModel();
            panel = new EntryPanel(table, model.getEntry(rowIndex));
        } else {
            if (table == 1) {
                EntryTableModel model = (EntryTableModel) delaysTable.getModel();
                panel = new EntryPanel(table, model.getEntry(rowIndex));
            } else {
                EntryTableModel model = (EntryTableModel) leadTimesTable.getModel();
                panel = new EntryPanel(table, model.getEntry(rowIndex));
            }
        }
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Entry",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (table == 0) {
                EntryTableModel model = (EntryTableModel) costsTable.getModel();
                if (panel.cmbValue.getSelectedIndex() == 0) {
                    model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new SingleValue(Double.parseDouble(panel.sV.getText().replace(",", ".")))));
                } else {
                    if (panel.sV.getText().isEmpty()) {
                        model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    } else {
                        model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sV.getText().replace(",", ".")), Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    }
                }
            }
            if (table == 1) {
                EntryTableModel model = (EntryTableModel) delaysTable.getModel();
                if (panel.cmbValue.getSelectedIndex() == 0) {
                    model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new SingleValue(Double.parseDouble(panel.sV.getText().replace(",", ".")))));
                } else {
                    if (panel.sV.getText().isEmpty()) {
                        model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    } else {
                        model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sV.getText().replace(",", ".")), Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    }
                }
            }
            if (table == 2) {
                EntryTableModel model = (EntryTableModel) leadTimesTable.getModel();
                if (panel.cmbValue.getSelectedIndex() == 0) {
                    model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new SingleValue(Double.parseDouble(panel.sV.getText().replace(",", ".")))));
                } else {
                    if (panel.sV.getText().isEmpty()) {
                        model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    } else {
                        model.updateEntry(rowIndex, new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sV.getText().replace(",", ".")), Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    }
                }
            }
        }
    }

    public void addEntry(int table) {
        EntryPanel panel = new EntryPanel(table);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Entry",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (table == 0) {
                EntryTableModel model = (EntryTableModel) costsTable.getModel();
                if (panel.cmbValue.getSelectedIndex() == 0) {
                    model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new SingleValue(Double.parseDouble(panel.sV.getText().replace(",", ".")))));
                } else {
                    if (panel.sV.getText().isEmpty()) {
                        model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    } else {
                        model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sV.getText().replace(",", ".")), Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    }
                }
            }
            if (table == 1) {
                EntryTableModel model = (EntryTableModel) delaysTable.getModel();
                if (panel.cmbValue.getSelectedIndex() == 0) {
                    model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new SingleValue(Double.parseDouble(panel.sV.getText().replace(",", ".")))));
                } else {
                    if (panel.sV.getText().isEmpty()) {
                        model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    } else {
                        model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sV.getText().replace(",", ".")), Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    }
                }
            }
            if (table == 2) {
                EntryTableModel model = (EntryTableModel) leadTimesTable.getModel();
                if (panel.cmbValue.getSelectedIndex() == 0) {
                    model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new SingleValue(Double.parseDouble(panel.sV.getText().replace(",", ".")))));
                } else {
                    if (panel.sV.getText().isEmpty()) {
                        model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    } else {
                        model.addEntry(new Entry(panel.name.getText(), panel.cmbCat.getSelectedItem().toString(), new RangeValue(Double.parseDouble(panel.sV.getText().replace(",", ".")), Double.parseDouble(panel.sVT.getText().replace(",", ".")))));
                    }
                }
            }
        }

    }

//    public void addCostRow(String name, String category, Value value) {
//        EntryTableModel model = (EntryTableModel) costsTable.getModel();
//        model.setList(new Entry(name, category, value));
//        model.fireTableDataChanged();
//
//    }
//    public void addDelayRow(String name, String category, Value value) {
//        EntryTableModel model = (EntryTableModel) delaysTable.getModel();
//        model.setList(new Entry(name, category, value));
//        model.fireTableDataChanged();
//    }
//
//    public void addLeadRow(String name, String category, Value value) {
//        EntryTableModel model = (EntryTableModel) leadTimesTable.getModel();
//        model.setList(new Entry(name, category, value));
//        model.fireTableDataChanged();
//    }
    private class EntryTableModel extends AbstractTableModel {

        private List<Entry> entries;

        public EntryTableModel(List<Entry> entries) {
            this.entries = entries;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Entry entry = entries.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return entry.getName();
                case 1:
                    return entry.getCategory();
                case 2:
                    if (entry.getValue() instanceof SingleValue) {
                        return entry.getValue().getValue();
                    } else {
                        return entry.getValue().getMin() + " - " + entry.getValue().getMax();
                    }
            }
            return null;
        }

        @Override
        public int getRowCount() {
            return entries.size();
        }

        public void addEntry(Entry entrie) {
            entries.add(entrie);
            fireTableDataChanged();
        }

        public List<Entry> getList() {
            return entries;
        }

        public Entry getEntry(int rowIndex) {
            return entries.get(rowIndex);
        }

        public void remove(int rowIndex) {
            entries.remove(rowIndex);
            fireTableDataChanged();
        }

        public void updateEntry(int rowIndex, Entry entry) {
            Entry uEntry = entries.get(rowIndex);
            uEntry.setName(entry.getName());
            uEntry.setCategory(entry.getCategory());
            uEntry.setValue(entry.getValue());
        }
    }

    public class EntryPanel extends JPanel {

        JTextField name;
        JComboBox cmbCat;
        JComboBox cmbValue;
        JTextField sV;
        JTextField sVT;
        JLabel val;
        JLabel max;

        public EntryPanel(int table, Entry entry) {
            DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
            Lookup.getDefault().lookup(DAOFactory.class);
            ArrayList<String> categoryList = new ArrayList();
            if (table == 0) {
                List<CostCategory> list;
                CostCategoryDAO cd = df.getCostCategoryDAO();
                list = cd.findAll();
                for (CostCategory c : list) {
                    categoryList.add(c.getIdentifier());
                }
            } else {
                List<TimeCategory> list;
                TimeCategoryDAO cd = df.getTimeCategoryDAO();
                list = cd.findAll();
                for (TimeCategory t : list) {
                    categoryList.add(t.getIdentifier());
                }

            }
            cmbCat = new JComboBox(categoryList.toArray());
            cmbCat.setSelectedItem(entry.getCategory());
            sV = new JTextField("");
            sVT = new JTextField("");
            sVT.setVisible(false);
            val = new JLabel("Value");
            max = new JLabel("Max");
            name = new JTextField(entry.getName());
            cmbValue = new JComboBox(new String[]{"Single value", "Range value"});
            if (entry.getValue() instanceof SingleValue) {
                cmbValue.setSelectedIndex(0);
                max.setVisible(false);
                sV.setText(String.valueOf(entry.getValue().getValue()));
            } else {
                cmbValue.setSelectedIndex(1);
                val.setText("Min");
                sV.setText(String.valueOf(entry.getValue().getMin()));
                sVT.setText(String.valueOf(entry.getValue().getMax()));
                val.setVisible(true);
                max.setVisible(true);
                sV.setVisible(true);
                sVT.setVisible(true);
            }
            setLayout(new GridLayout(0, 1));
            add(new JLabel("Name:"));
            add(name);
            add(new JLabel("Category:"));
            add(cmbCat);
            add(cmbValue);
            add(val);
            add(sV);
            add(max);
            add(sVT);
            cmbValue.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cmbValue.getSelectedIndex() == 0) {
                        val.setText("Value");
                        val.setVisible(true);
                        max.setVisible(false);
                        sV.setVisible(true);
                        sVT.setVisible(false);
                    } else {
                        val.setText("Min");
                        val.setVisible(true);
                        max.setVisible(true);
                        sV.setVisible(true);
                        sVT.setVisible(true);
                    }
                }
            });
        }

        public EntryPanel(int table) {
            DAOFactory df = Lookup.getDefault().lookup(DAOFactory.class);
            Lookup.getDefault().lookup(DAOFactory.class);
            ArrayList<String> categoryList = new ArrayList();
            if (table == 0) {
                List<CostCategory> list;
                CostCategoryDAO cd = df.getCostCategoryDAO();
                list = cd.findAll();
                for (CostCategory c : list) {
                    categoryList.add(c.getIdentifier());
                }
            } else {
                List<TimeCategory> list;
                TimeCategoryDAO cd = df.getTimeCategoryDAO();
                list = cd.findAll();
                for (TimeCategory t : list) {
                    categoryList.add(t.getIdentifier());
                }
            }
            cmbCat = new JComboBox(categoryList.toArray());
            name = new JTextField("");
            cmbValue = new JComboBox(new String[]{"Single value", "Range value"});
            sV = new JTextField("");
            sVT = new JTextField("");
            val = new JLabel("Value");
            max = new JLabel("Max");
            max.setVisible(false);
            setLayout(new GridLayout(0, 1));
            add(new JLabel("Name:"));
            add(name);
            add(new JLabel("Category:"));
            add(cmbCat);
            add(new JLabel("Value:"));
            add(cmbValue);
            add(val);
            add(sV);
            add(max);
            sVT.setVisible(false);
            add(sVT);
            cmbValue.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (cmbValue.getSelectedIndex() == 0) {
                        val.setText("Value");
                        val.setVisible(true);
                        max.setVisible(false);
                        sV.setVisible(true);
                        sVT.setVisible(false);
                    } else {
                        val.setText("Min");
                        val.setVisible(true);
                        max.setVisible(true);
                        sV.setVisible(true);
                        sVT.setVisible(true);
                    }
                }
            });
        }
    }
}
