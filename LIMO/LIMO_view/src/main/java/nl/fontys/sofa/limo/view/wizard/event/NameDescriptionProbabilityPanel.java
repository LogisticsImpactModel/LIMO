package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.api.service.distribution.DistributionFactory;
import nl.fontys.sofa.limo.domain.component.event.Event;
import org.openide.util.Lookup;

public final class NameDescriptionProbabilityPanel extends JPanel {

    private JLabel lblName;
    JTextField tfName;

    private javax.swing.JLabel lblDescription;
    JTextArea tfDescription;

    private JLabel lblDistributionType;
    private JComboBox<String> cbDistributionType;

    private JLabel lblParameters;
    private JTable tParameters;

    private Event event;
    private DistributionFactory dtf;

    public NameDescriptionProbabilityPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("NAME_AND_PROBABILITY");
    }

    private void initComponents() {
        dtf = Lookup.getDefault().lookup(DistributionFactory.class);
        List<String> distTypes = Arrays.asList(dtf.getDistributionTypes());
        Collections.sort(distTypes);
        String[] cbModel = new String[distTypes.size()];
        distTypes.toArray(cbModel);

        lblName = new javax.swing.JLabel(java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("NAME"));
        tfName = new javax.swing.JTextField();
        lblDescription = new javax.swing.JLabel("Description");
        tfDescription = new javax.swing.JTextArea();
        tfDescription.setRows(4);
        tfDescription.setBorder(tfName.getBorder());
        tfDescription.setSize(tfDescription.getHeight(), tfName.getWidth());
        lblDistributionType = new javax.swing.JLabel(java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("DISTRIBUTION_TYPE"));
        cbDistributionType = new javax.swing.JComboBox<>(cbModel);
        lblParameters = new javax.swing.JLabel(java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("PARAMETERS"));
        tParameters = new javax.swing.JTable();
        tParameters.setModel(new DistributionParameterTableModel());
        tParameters.setShowGrid(true);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(lblName, c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        add(tfName, c);

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(lblDescription, c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        add(tfDescription, c);

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(lblDistributionType, c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        add(cbDistributionType, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        add(new javax.swing.JLabel("   "), c);

        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        add(lblParameters, c);

        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 4;
        c.gridheight = 3;
        add(tParameters, c);

        event = new Event();

        cbDistributionType.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                event.setProbability(dtf.getDistributionTypeByName((String) cbDistributionType.getSelectedItem()));
                ((AbstractTableModel) tParameters.getModel()).fireTableDataChanged();
            }
        });

        tfName.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateEventFromInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateEventFromInput();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateEventFromInput();
            }
        });

        cbDistributionType.setSelectedIndex(0);
    }

    public void update(Event event) {
        if (event != null) {
            tfName.setText(event.getName());
            tfDescription.setText(event.getDescription());
            if (event.getProbability() != null) {
                cbDistributionType.setSelectedItem(dtf.getNameForDistributionType(event.getProbability().getClass()));
            } else {
                cbDistributionType.setSelectedIndex(0);
            }
            ((AbstractTableModel) tParameters.getModel()).fireTableDataChanged();
        }
    }

    public Event getEvent() {
        updateEventFromInput();
        return event;
    }

    private void updateEventFromInput() {
        event.setName(tfName.getText());
        event.setDescription(tfDescription.getText());
    }

    private class DistributionParameterTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return event.getProbability() == null ? 0 : event.getProbability().getNames().size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String inputValueName = event.getProbability().getNames().get(rowIndex);

            if (columnIndex == 0) {
                return inputValueName;
            } else {
                return event.getProbability().getValue(inputValueName);
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public String getColumnName(int column) {
            return column == 0 ? java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("NAME") : java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("VALUE");
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 1) {
                String inputValueName = event.getProbability().getNames().get(rowIndex);
                Number n;

                Class<?> inputType = event.getProbability().getType(inputValueName);

                if (inputType.equals(Double.class)) {
                    try {
                        n = Double.parseDouble((String) aValue);
                    } catch (NumberFormatException nfe) {
                        n = null;
                        javax.swing.JOptionPane.showMessageDialog(tParameters, java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("REQUIRES_FLOATING-POINT_VALUE"), java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("NOT_FLOATING-POINT"), JOptionPane.WARNING_MESSAGE);
                    }
                } else if (inputType.equals(Integer.class)) {
                    try {
                        n = Integer.parseInt((String) aValue);
                    } catch (NumberFormatException nfe) {
                        n = null;
                        javax.swing.JOptionPane.showMessageDialog(tParameters, java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("REQUIRES_INTERGER_VALUE"), java.util.ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle").getString("NOT_INTERGER"), JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    n = 0;
                }

                if (n != null) {
                    event.getProbability().setInputValue(inputValueName, n);
                }

            }
        }
    }

}
