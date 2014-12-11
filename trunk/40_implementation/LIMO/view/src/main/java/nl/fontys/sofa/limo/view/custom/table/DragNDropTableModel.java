package nl.fontys.sofa.limo.view.custom.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Matthias Brück
 */
public class DragNDropTableModel extends AbstractTableModel {

    private final String[] columnNames;
    private final List<List<Object>> values;
    private final Class[] classes;
    private final List<TableModelListener> tableModelListeners;

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

    public void addRow(List<Object> newRow) {
        if (listIsValid(newRow)) {
            values.add(newRow);
            fireTableDataChanged();
        }
    }

    private boolean listIsValid(List<Object> newRow) {
        if (newRow.size() < getColumnCount()) {
            System.out.println("Row got wrong size.");
            return false;
        }
        for (int i = 0; i < newRow.size(); i++) {
            if (!(classes[i]).isInstance(newRow.get(i))) {
                System.out.println("Column " + i + " should be class " + classes[i] + " but is class " + newRow.get(i).getClass());
                return false;
            }
        }
        return true;
    }

    public void removeRow(int rowNumber) {
        if (rowNumber < getRowCount()) {
            values.remove(rowNumber);
            fireTableDataChanged();
        }
    }

    public List<List<Object>> getValues() {
        return values;
    }
}
