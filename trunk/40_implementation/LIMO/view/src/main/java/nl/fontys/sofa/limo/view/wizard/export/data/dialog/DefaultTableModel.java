package nl.fontys.sofa.limo.view.wizard.export.data.dialog;

/**
 * Extension of the DefaultTableModel. Adds functionality to change editability.
 *
 * @author Matthias Brück
 */
public class DefaultTableModel extends javax.swing.table.DefaultTableModel {

    private final boolean[] editable;
    private final Class[] classes;

    /**
     *
     * @param data data
     * @param columnHeader column header.
     * @param editable If null editable is always false. If its too small it
     * will be false for missing columns. If it's to big the columns that are to
     * much will be ignored. Does not support adding and removing of columns.
     */
    public DefaultTableModel(Object[][] data, Object[] columnHeader, boolean[] editable, Class[] classes) {
        super(data, columnHeader);
        if (editable == null) {
            this.editable = new boolean[columnHeader.length];
            for (boolean f : this.editable) {
                f = false;
            }
        } else {
            this.editable = editable;
        }
        this.classes = classes;
    }

    /**
     * Sets a columns editable state.
     *
     * @param isEditable the state.
     * @param column number of the column starting with 1. If the number is too
     * high nothing will happen.
     */
    public void setEditable(boolean isEditable, int column) {
        if (editable.length >= column && column > 0) {
            editable[column - 1] = isEditable;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex < classes.length && columnIndex >= 0) {
            return classes[columnIndex];
        }
        return Object.class;
    }

    /**
     * Returns weather a column is editable or not. First column is 1. Row gets
     * ignored.
     *
     * @param row Gets ignored.
     * @param column Number of the column.
     * @return True if the column is editable, false if not.
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        if (editable.length >= column && column > 0) {
            return editable[column];
        }
        return false;
    }

}
