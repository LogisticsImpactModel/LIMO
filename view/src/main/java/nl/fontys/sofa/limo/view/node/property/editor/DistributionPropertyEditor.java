package nl.fontys.sofa.limo.view.node.property.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import nl.fontys.sofa.limo.api.service.distribution.DistributionFactory;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;
import org.openide.nodes.PropertyEditorRegistration;
import org.openide.util.Lookup;

/**
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 */
@PropertyEditorRegistration(targetType = Distribution.class)
public class DistributionPropertyEditor extends PropertyEditorSupport {

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        return new CustomEditor();
    }

    @Override
    public String getAsText() {
        return ((Distribution) getValue()).getClass().getSimpleName();
    }

    /**
     * Convenience method to get editor value as distribution.
     *
     * @return
     */
    protected Distribution getDistribution() {
        return (Distribution) getValue();
    }

    /**
     * Private class. Defining the custom distribution property editor.
     */
    private class CustomEditor extends JPanel {

        private final JLabel distributionTypeLabel;
        private JComboBox<String> distributionTypeComboBox;

        private JPanel parametersLabel;
        private JTable parametersTable;

        private DistributionFactory distributionFactory;

        /**
         * Create editor and init UI.
         */
        public CustomEditor() {

            // Create layout
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.NORTH;
            c.insets = new Insets(3, 3, 3, 3);

            // Create parameter input
            parametersLabel = new javax.swing.JPanel();
            parametersLabel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
                    LIMOResourceBundle.getString("PARAMETERS"),
                    TitledBorder.LEFT,
                    TitledBorder.TOP
            ));
            parametersLabel.setLayout(new BorderLayout());
            parametersTable = new javax.swing.JTable() {

                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    // Only select editable cells, not whole row
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
            parametersTable.setModel(new DistributionParameterTableModel());
            parametersTable.setShowGrid(true);
            parametersTable.putClientProperty("terminateEditOnFocusLost", true);
            DefaultCellEditor singleClick = (DefaultCellEditor) parametersTable.getDefaultEditor(parametersTable.getColumnClass(1));
            singleClick.setClickCountToStart(1);
            parametersTable.setDefaultEditor(parametersTable.getColumnClass(1), singleClick);
            parametersLabel.add(parametersTable, BorderLayout.CENTER);

            // Get distributions and add to combo box
            distributionFactory = Lookup.getDefault().lookup(DistributionFactory.class);
            List<String> distTypes = Arrays.asList(distributionFactory.getDistributionTypes());
            Collections.sort(distTypes);
            String[] cbModel = distTypes.toArray(new String[distTypes.size()]);
            distributionTypeLabel = new javax.swing.JLabel(LIMOResourceBundle.getString("DISTRIBUTION_TYPE"));
            distributionTypeComboBox = new javax.swing.JComboBox<>(cbModel);
            String nameForDistributionType = distributionFactory.getNameForDistributionType(getDistribution().getClass());
            distributionTypeComboBox.getModel().setSelectedItem(nameForDistributionType);
            distributionTypeComboBox.addActionListener((ActionEvent e) -> {
                setValue(distributionFactory.getDistributionTypeByName((String) distributionTypeComboBox.getSelectedItem()));
                ((AbstractTableModel) parametersTable.getModel()).fireTableDataChanged();
            });

            c.weightx = 0.3;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            add(distributionTypeLabel, c);
            c.weightx = 0.7;
            c.gridx = 1;
            c.gridy = 0;
            c.gridwidth = 3;
            add(distributionTypeComboBox, c);

            c.weightx = 1;
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 4;
            add(parametersLabel, c);
        }

        /**
         * Table model for distribution property editor.
         */
        private class DistributionParameterTableModel extends AbstractTableModel {

            @Override
            public int getRowCount() {
                return getDistribution() == null ? 0 : getDistribution().getNames().size();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                String inputValueName = getDistribution().getNames().get(rowIndex);

                if (columnIndex == 0) {
                    return inputValueName;
                } else {
                    return getDistribution().getValue(inputValueName);
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public String getColumnName(int column) {
                return column == 0 ? LIMOResourceBundle.getString("NAME") : LIMOResourceBundle.getString("VALUE");
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex == 1;
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                if (columnIndex == 1) {
                    String inputValueName = getDistribution().getNames().get(rowIndex);
                    Number n;

                    Class<?> inputType = getDistribution().getType(inputValueName);

                    if (inputType.equals(Double.class)) {
                        try {
                            n = Double.parseDouble((String) aValue);
                        } catch (NumberFormatException nfe) {
                            n = null;
                            javax.swing.JOptionPane.showMessageDialog(parametersTable, LIMOResourceBundle.getString("REQUIRES_FLOATING-POINT_VALUE"), LIMOResourceBundle.getString("NOT_FLOATING-POINT"), JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (inputType.equals(Integer.class)) {
                        try {
                            n = Integer.parseInt((String) aValue);
                        } catch (NumberFormatException nfe) {
                            n = null;
                            javax.swing.JOptionPane.showMessageDialog(parametersTable, LIMOResourceBundle.getString("REQUIRES_INTERGER_VALUE"), LIMOResourceBundle.getString("NOT_INTERGER"), JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        n = 0;
                    }

                    if (n != null) {
                        getDistribution().setInputValue(inputValueName, n);
                    }

                    Distribution newProp = distributionFactory.getDistributionTypeByName(distributionFactory.getNameForDistributionType(getDistribution().getClass()));
                    newProp.setInputValues(getDistribution().getInputValues());
                    setValue(newProp);

                    fireTableCellUpdated(rowIndex, columnIndex);
                }
            }
        }
    }
}
