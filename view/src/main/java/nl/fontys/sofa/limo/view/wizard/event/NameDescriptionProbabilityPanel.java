package nl.fontys.sofa.limo.view.wizard.event;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import nl.fontys.sofa.limo.api.service.distribution.DistributionFactory;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.event.distribution.DiscreteDistribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.domain.component.event.distribution.PoissonDistribution;
import nl.fontys.sofa.limo.view.custom.table.DistributionParameterTableModel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.util.Lookup;

/**
 * Panel to enter name, description and probability of an event.
 *
 * @author Sven Mäurer
 */
public final class NameDescriptionProbabilityPanel extends JPanel {

    private JTextField nameTextField;
    private JTextArea descriptionTextArea;
    private JComboBox<String> distributionTypeComboBox;
    private JPanel parametersLabel;
    private JLabel noUpperLabel;
    private JTable parametersTable;
    private JTextArea distributionDescription;
    private JCheckBox noUpperCheckbox;
    private GridBagConstraints c;

    private DistributionFactory distributionFactory;
    private DistributionParameterTableModel tableModel;

    public NameDescriptionProbabilityPanel() {
        initComponents();
    }

    @Override
    public String getName() {
        return LIMOResourceBundle.getString("BASIC_DATA");
    }

    private void initComponents() {
        assignComponents();
        c = initLayout();
        setComponentProperties();
        initDistribution();

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        add(new JLabel(LIMOResourceBundle.getString("NAME")), c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        add(nameTextField, c);

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        add(new JLabel(LIMOResourceBundle.getString("DESCRIPTION")), c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        add(descriptionTextArea, c);

        c.weightx = 0.3;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        add(new JLabel(LIMOResourceBundle.getString("DISTRIBUTION_TYPE")), c);
        c.weightx = 0.7;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        add(distributionTypeComboBox, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 4;
        add(distributionDescription, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        add(parametersLabel, c);
    }

    /**
     * Change the view behavior of the components especially let the table look
     * nice.
     */
    private void setComponentProperties() {
        descriptionTextArea.setRows(4);
        parametersLabel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                LIMOResourceBundle.getString("PARAMETERS"),
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));
        parametersLabel.setLayout(new BorderLayout());
        tableModel = new DistributionParameterTableModel(parametersTable);
        parametersTable.setModel(tableModel);
        parametersTable.setShowGrid(true);
        parametersTable.putClientProperty("terminateEditOnFocusLost", true);
        DefaultCellEditor singleClick = (DefaultCellEditor) parametersTable.getDefaultEditor(parametersTable.getColumnClass(1));
        singleClick.setClickCountToStart(1);
        parametersTable.setDefaultEditor(parametersTable.getColumnClass(1), singleClick);
        parametersLabel.add(parametersTable, BorderLayout.CENTER);
    }

    private void assignComponents() {
        nameTextField = new JTextField();
        descriptionTextArea = new JTextArea();
        distributionDescription = new JTextArea();
        parametersLabel = new JPanel();
        noUpperLabel = new JLabel("No Upper Bound:");
        noUpperCheckbox = new JCheckBox();
        noUpperCheckbox.addActionListener((ActionEvent ae) -> {
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String bla = String.valueOf(tableModel.getValueAt(i, 0));
                if(bla.equals("Upper Bound") && noUpperCheckbox.isSelected())
                {
                    tableModel.setValueAt("1000000000", i, 1);
                }
                
                if(bla.equals("Upper Bound") && !noUpperCheckbox.isSelected())
                {
                    tableModel.setValueAt("0", i, 1);
                }
            }
        });
        parametersTable = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isCellSelected(row, column)) {
                    c.setBackground(getSelectionBackground());
                } else if (column == 0) {
                    c.setBackground(UIManager.getColor("Panel.background"));
                } else {
                    c.setBackground(null);
                }
                return c;
            }

        };
    }

    private GridBagConstraints initLayout() {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(3, 3, 3, 3);
        return c;
    }

    /**
     * Initialize the distribution combo box including the selection behavior.
     */
    private void initDistribution() {
        distributionDescription.setEditable(false);
        distributionFactory = Lookup.getDefault().lookup(DistributionFactory.class);
        List<String> distTypes = Arrays.asList(distributionFactory.getDistributionTypes());
        Collections.sort(distTypes);
        String[] cbModel = distTypes.toArray(new String[distTypes.size()]);
        distributionTypeComboBox = new JComboBox<>(cbModel);
        distributionTypeComboBox.addActionListener((ActionEvent e) -> {
            Distribution prop = distributionFactory.getDistributionTypeByName((String) distributionTypeComboBox.getSelectedItem());
            tableModel.setProperty(prop);
            tableModel.fireTableDataChanged();
            distributionDescription.setText(prop.getDescription());
            checkVisibility();
        });
        distributionTypeComboBox.setSelectedItem(distributionFactory.getNameForDistributionType(DiscreteDistribution.class));
        distributionDescription.setText(distributionFactory.getDistributionTypeByName(distributionTypeComboBox.getModel().getElementAt(0)).getDescription());
    }

    /**
     * Update the name, description and the probability table based on the
     * event.
     *
     * @param event to be used.
     */
    public void updateEvent(Event event) {
        if (event != null) {
            nameTextField.setText(event.getName());
            descriptionTextArea.setText(event.getDescription());
            Distribution probability = event.getProbability();
            if (probability != null) {
                String nameForDistributionType = distributionFactory.getNameForDistributionType(probability.getClass());
                distributionTypeComboBox.getModel().setSelectedItem(nameForDistributionType);
                tableModel.setProperty(probability);
                tableModel.fireTableDataChanged();
                distributionDescription.setText(probability.getDescription());
            } else {
                distributionTypeComboBox.setSelectedItem(distributionFactory.getNameForDistributionType(PoissonDistribution.class));
                distributionDescription.setText(distributionFactory.getDistributionTypeByName(distributionTypeComboBox.getModel().getElementAt(0)).getDescription());
            }
        } else {
            nameTextField.setText("");
            descriptionTextArea.setText("");
            distributionTypeComboBox.setSelectedItem(distributionFactory.getNameForDistributionType(PoissonDistribution.class));
            distributionDescription.setText(distributionFactory.getDistributionTypeByName(distributionTypeComboBox.getModel().getElementAt(0)).getDescription());
        }
    }

    void checkVisibility() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String bla = String.valueOf(tableModel.getValueAt(i, 0));
            if(bla.equals("Upper Bound") )
            {
                c.weightx = 0.3;
                c.gridx = 0;
                c.gridy = 5;
                c.gridwidth = 1;
                add(noUpperLabel, c);

                c.weightx = 1;
                c.gridx = 1;
                c.gridy = 5;
                c.gridwidth = 3;
                add(noUpperCheckbox, c);
            }
            else
            {
                remove(noUpperLabel);
                remove(noUpperCheckbox);
            }
        }
    }
    
    String getNameInput() {
        return nameTextField.getText();
    }

    String getDescriptionInput() {
        return descriptionTextArea.getText();
    }

    Distribution getProbability() {
        return tableModel.getProbability();
    }

}
