package nl.fontys.sofa.limo.view.custom.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import nl.fontys.sofa.limo.view.util.LIMOResourceBundle;

/**
 * This class represents a Drag 'n Drop TableModel. It's basically a normal
 * table model but it is used in the Drag 'n Drop Table, which has implemented
 * darg 'n drop functionality.
 *
 * @author Matthias Brück
 */
public class DragNDropTableModel extends AbstractTableModel {

    private final String[] columnNames;
    private final List<List<Object>> values;
    private final Class[] classes;
    private final List<TableModelListener> tableModelListeners;

    /**
     * Creates a new DragNDropTableModel.
     *
     * @param columnNames The names of the columns.
     * @param values The values that have to be displayed in the table.
     * @param classes The Classestypes of the values in the different columns.
     */
    public DragNDropTableModel(String[] columnNames, List<List<Object>> values, Class[] classes) {
        this.columnNames = columnNames;
        this.values = values;
        this.classes = classes;
        tableModelListeners = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowCount() > 0) {
            if (rowIndex < getRowCount() && rowIndex >= 0) {
                if (columnIndex < getColumnCount() && columnIndex >= 0) {
                    return values.get(rowIndex).get(columnIndex);
                }
            }
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (getRowCount() > 0) {
            if (rowIndex < getRowCount() && rowIndex >= 0) {
                if (columnIndex < getColumnCount() && columnIndex >= 0) {
                    values.get(rowIndex).set(columnIndex, aValue);
                }
            }
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        tableModelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        tableModelListeners.remove(l);
    }

    /**
     * Adds a new row to the model. The row gets only added when the list is ok,
     * meaning that the classtypes are matching.
     *
     * @param newRow The row that has to be added.
     */
    public void addRow(List<Object> newRow) {
        if (listIsValid(newRow)) {
            values.add(newRow);
            fireTableDataChanged();
        }
    }

    /**
     * Checks a list if it fits to the tablemodel.
     *
     * @param newRow The row that has to be checked.
     * @return True, if the list fits to the table, false when not.
     */
    private boolean listIsValid(List<Object> newRow) {
        if (newRow.size() < getColumnCount()) {
            System.out.println(LIMOResourceBundle.getString("ROW_WRONG_SIZE"));
            return false;
        }
        for (int i = 0; i < newRow.size(); i++) {
            if (!(classes[i]).isInstance(newRow.get(i))) {
                System.out.println(LIMOResourceBundle.getString("COLUMN_WRONG_CLASS", i, classes[i], newRow.get(i).getClass()));
                return false;
            }
        }
        return true;
    }

    /**
     * Removes a row from the table. Does nothing if the number is an illegal
     * number (smaller 0 oder bigger than number of rows).
     *
     * @param rowNumber The number of the row that has to be removed.
     */
    public void removeRow(int rowNumber) {
        if (rowNumber < getRowCount() && rowNumber >= 0) {
            values.remove(rowNumber);
            fireTableDataChanged();
        }
    }

    /**
     * Returns the table as a List, each row is represented as an additional
     * list inside.
     *
     * @return The table.
     */
    public List<List<Object>> getValues() {
        return values;
    }
}
