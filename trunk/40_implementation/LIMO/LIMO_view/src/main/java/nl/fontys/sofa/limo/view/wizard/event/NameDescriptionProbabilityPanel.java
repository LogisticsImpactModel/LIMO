package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.api.service.distribution.DistributionFactory;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import org.openide.util.Lookup;

public final class NameDescriptionProbabilityPanel extends JPanel {

    private JLabel nameLabel;
    JTextField nameTextField;

    private javax.swing.JLabel descriptionLabel;
    JTextArea descriptionTextArea;

    private JLabel distributionTypeLabel;
    private JComboBox<String> distributionTypeComboBox;

    private JLabel parametersLabel;
    private JTable parametersTable;

    private Event event;
    private DistributionFactory distributionFactory;

    private final ResourceBundle bundle;

    public NameDescriptionProbabilityPanel() {
        bundle = ResourceBundle.getBundle("nl/fontys/sofa/limo/view/wizard/event/Bundle");
        initComponents();
    }

    @Override
    public String getName() {
        return bundle.getString("NAME_AND_PROBABILITY");
    }

    private void initComponents() {
        GridBagConstraints c = initLayout();
        initName();
        initDistribution();

        descriptionLabel = new javax.swing.JLabel(bundle.getString("DESCRIPTION"));
        descriptionTextArea = new javax.swing.JTextArea();
        descriptionTextArea.setRows(4);
        descriptionTextArea.setBorder(nameTextField.getBorder());
        descriptionTextArea.setSize(descriptionTextArea.getHeight(), nameTextField.getWidth());

        parametersLabel = new javax.swing.JLabel(bundle.getString("PARAMETERS"));
        parametersTable = new javax.swing.JTable();
        parametersTable.setModel(new DistributionParameterTableModel());
        parametersTable.setShowGrid(true);

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(nameLabel, c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        add(nameTextField, c);

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(descriptionLabel, c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        add(descriptionTextArea, c);

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(distributionTypeLabel, c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        add(distributionTypeComboBox, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        add(new javax.swing.JLabel("   "), c);

        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        add(parametersLabel, c);

        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 4;
        c.gridheight = 3;
        add(parametersTable, c);

    }

    private GridBagConstraints initLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private void initName() {
        nameLabel = new javax.swing.JLabel(bundle.getString("NAME"));
        nameTextField = new javax.swing.JTextField();
    }

    private void initDistribution() {
        distributionFactory = Lookup.getDefault().lookup(DistributionFactory.class);
        List<String> distTypes = Arrays.asList(distributionFactory.getDistributionTypes());
        Collections.sort(distTypes);
        String[] cbModel = new String[distTypes.size()];
        distTypes.toArray(cbModel);
        distributionTypeLabel = new javax.swing.JLabel(bundle.getString("DISTRIBUTION_TYPE"));
        distributionTypeComboBox = new javax.swing.JComboBox<>(cbModel);
        distributionTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                event.setProbability(distributionFactory.getDistributionTypeByName((String) distributionTypeComboBox.getSelectedItem()));
                ((AbstractTableModel) parametersTable.getModel()).fireTableDataChanged();
            }
        });
    }

    public void update(Event event) {
        this.event = event;
        nameTextField.setText(event.getName());
        descriptionTextArea.setText(event.getDescription());
        Distribution probability = event.getProbability();
        if (probability != null) {
            String nameForDistributionType = probability.getClass().getSimpleName();
            distributionTypeComboBox.setSelectedItem(nameForDistributionType);
        } else {
            distributionTypeComboBox.setSelectedIndex(0);
        }
        ((AbstractTableModel) parametersTable.getModel()).fireTableDataChanged();
    }

    public Event getEvent() {
        updateEventFromInput();
        return event;
    }

    private void updateEventFromInput() {
        event.setName(nameTextField.getText());
        event.setDescription(descriptionTextArea.getText());
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
            return column == 0 ? bundle.getString("NAME") : bundle.getString("VALUE");
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
                        javax.swing.JOptionPane.showMessageDialog(parametersTable, bundle.getString("REQUIRES_FLOATING-POINT_VALUE"), bundle.getString("NOT_FLOATING-POINT"), JOptionPane.WARNING_MESSAGE);
                    }
                } else if (inputType.equals(Integer.class)) {
                    try {
                        n = Integer.parseInt((String) aValue);
                    } catch (NumberFormatException nfe) {
                        n = null;
                        javax.swing.JOptionPane.showMessageDialog(parametersTable, bundle.getString("REQUIRES_INTERGER_VALUE"), bundle.getString("NOT_INTERGER"), JOptionPane.WARNING_MESSAGE);
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
