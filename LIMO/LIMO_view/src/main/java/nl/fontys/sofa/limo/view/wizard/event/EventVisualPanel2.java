/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.Lookup;

public final class EventVisualPanel2 extends JPanel {

    /**
     * Creates new form HubVisualPanel2
     */
    public EventVisualPanel2() {
        initComponents();
    }

    @Override
    public String getName() {
        return "Name and Probability";
    }

    private void initComponents() {
//        dtf = Lookup.getDefault().lookup(DistributionTypeFactory.class);
//        ArrayList<String> distTypes = new ArrayList<>(Arrays.asList(dtf.getDistributionTypes()));
//        distTypes.add(0, "-");
//        String[] cbModel = new String[distTypes.size()];
//        distTypes.toArray(cbModel);
//
//        lblName = new javax.swing.JLabel("Name");
//        tfName = new javax.swing.JTextField();
//        lblDistributionType = new javax.swing.JLabel("Distribution Type");
//        cbDistributionType = new javax.swing.JComboBox<>(cbModel);
//        lblParameters = new javax.swing.JLabel("Parameters");
//        tParameters = new javax.swing.JTable(new AbstractTableModel() {
//
//            @Override
//            public int getRowCount() {
//                return event.getProbability() == null ? 0 : event.getProbability().getInputValueNames().size();
//            }
//
//            @Override
//            public int getColumnCount() {
//                return 2;
//            }
//
//            @Override
//            public Object getValueAt(int rowIndex, int columnIndex) {
//                String inputValueName = event.getProbability().getInputValueNames().get(rowIndex);
//
//                if (columnIndex == 0) {
//                    return inputValueName;
//                } else {
//                    return event.getProbability().getInputValue(inputValueName);
//                }
//            }
//
//            @Override
//            public Class<?> getColumnClass(int columnIndex) {
//                return String.class;
//            }
//
//            @Override
//            public String getColumnName(int column) {
//                return column == 0 ? "Name" : "Value";
//            }
//
//            @Override
//            public boolean isCellEditable(int rowIndex, int columnIndex) {
//                return columnIndex == 1;
//            }
//
//            @Override
//            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//                if (columnIndex == 1) {
//                    String inputValueName = event.getProbability().getInputValueNames().get(rowIndex);
//                    Number n;
//
//                    Class<?> inputType = event.getProbability().getInputValueType(inputValueName);
//
//                    if (inputType.equals(Double.class)) {
//                        try {
//                            n = Double.parseDouble((String) aValue);
//                        } catch (NumberFormatException nfe) {
//                            n = null;
//                            javax.swing.JOptionPane.showMessageDialog(tParameters, "This cell requires a floating-point value!", "Not a floating-point number!", JOptionPane.WARNING_MESSAGE);
//                        }
//                    } else if (inputType.equals(Integer.class)) {
//                        try {
//                            n = Integer.parseInt((String) aValue);
//                        } catch (NumberFormatException nfe) {
//                            n = null;
//                            javax.swing.JOptionPane.showMessageDialog(tParameters, "This cell requires an interger value!", "Not an interger!", JOptionPane.WARNING_MESSAGE);
//                        }
//                    } else {
//                        n = 0;
//                    }
//
//                    if (n != null) {
//                        event.getProbability().setInputValue(inputValueName, n);
//                    }
//
//                }
//            }
//        });
//        tParameters.setShowGrid(true);
//
//        setLayout(new GridBagLayout());
//        GridBagConstraints c = new GridBagConstraints();
//        c.fill = GridBagConstraints.HORIZONTAL;
//
//        c.weightx = 0.3;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.gridwidth = 1;
//        add(lblName, c);
//        c.weightx = 0.7;
//        c.gridx = 1;
//        c.gridy = 0;
//        c.gridwidth = 3;
//        add(tfName, c);
//
//        c.weightx = 0.3;
//        c.gridx = 0;
//        c.gridy = 1;
//        c.gridwidth = 1;
//        add(lblDistributionType, c);
//        c.weightx = 0.7;
//        c.gridx = 1;
//        c.gridy = 1;
//        c.gridwidth = 3;
//        add(cbDistributionType, c);
//
//        c.gridx = 0;
//        c.gridy = 2;
//        c.gridwidth = 4;
//        add(new javax.swing.JLabel("   "), c);
//
//        c.weightx = 1.0;
//        c.gridx = 0;
//        c.gridy = 3;
//        c.gridwidth = 4;
//        add(lblParameters, c);
//
//        c.weightx = 1.0;
//        c.gridx = 0;
//        c.gridy = 4;
//        c.gridwidth = 4;
//        c.gridheight = 3;
//        add(new JScrollPane(tParameters), c);
//
//        event = new Event();
//
//        cbDistributionType.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                event.setProbability(dtf.getDistributionTypeByName((String) cbDistributionType.getSelectedItem()));
//                ((AbstractTableModel) tParameters.getModel()).fireTableDataChanged();
//            }
//        });
//
//        tfName.getDocument().addDocumentListener(new DocumentListener() {
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                event.setIdentifier(tfName.getText());
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                event.setIdentifier(tfName.getText());
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                event.setIdentifier(tfName.getText());
//            }
//        });
    }

    public void update(Event event) {
//        if (event != null) {
//            tfName.setText(event.getIdentifier());
//            if (event.getProbability() != null) {
//                cbDistributionType.setSelectedItem(dtf.getNameForDistributionType(event.getProbability().getClass()));
//            } else {
//                cbDistributionType.setSelectedIndex(0);
//            }
//            ((AbstractTableModel) tParameters.getModel()).fireTableDataChanged();
//        }
    }

    public Event getEvent() {
        event.setUniqueIdentifier(tfName.getText());
        return event;
    }

    private javax.swing.JLabel lblName;
    private javax.swing.JTextField tfName;

    private javax.swing.JLabel lblDistributionType;
    private javax.swing.JComboBox<String> cbDistributionType;

    private javax.swing.JLabel lblParameters;
    private javax.swing.JTable tParameters;

    private Event event;
    //private DistributionTypeD dtf;
}
