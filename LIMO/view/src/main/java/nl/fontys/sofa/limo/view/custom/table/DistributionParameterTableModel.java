package nl.fontys.sofa.limo.view.custom.table;

import java.awt.Component;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.domain.component.event.distribution.Distribution;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * Table Model for the distribution of an event.
 *
 * @author Sven Mäurer
 */
public class DistributionParameterTableModel extends AbstractTableModel {

    private Distribution prop;
    private final Component parent;

    public DistributionParameterTableModel(Component parent) {
        this.parent = parent;
    }

    @Override
    public int getRowCount() {
        return prop == null ? 0 : prop.getNames().size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String inputValueName = prop.getNames().get(rowIndex);

        if (columnIndex == 0) {
            return inputValueName;
        } else {
            return prop.getValue(inputValueName);
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

    /**
     * Set a value in the table model. It is either a double or an integer.
     *
     * @param aValue to be set.
     * @param rowIndex of the value.
     * @param columnIndex of the value.
     */
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            String inputValueName = prop.getNames().get(rowIndex);
            Number n;

            Class<?> inputType = prop.getType(inputValueName);

            if (inputType.equals(Double.class)) {
                try {
                    n = Double.parseDouble((String) aValue);
                } catch (NumberFormatException nfe) {
                    n = null;
                    JOptionPane.showMessageDialog(parent, LIMOResourceBundle.getString("REQUIRES_FLOATING-POINT_VALUE"), LIMOResourceBundle.getString("NOT_FLOATING-POINT"), JOptionPane.WARNING_MESSAGE);
                }
            } else if (inputType.equals(Integer.class)) {
                try {
                    n = Integer.parseInt((String) aValue);
                } catch (NumberFormatException nfe) {
                    n = null;
                    JOptionPane.showMessageDialog(parent, LIMOResourceBundle.getString("REQUIRES_INTERGER_VALUE"), LIMOResourceBundle.getString("NOT_INTERGER"), JOptionPane.WARNING_MESSAGE);
                }
            } else {
                n = 0;
            }

            if (n != null) {
                prop.setInputValue(inputValueName, n);
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public void setProperty(Distribution prop) {
        this.prop = prop;
    }

    public Distribution getProbability() {
        return this.prop;
    }
}
