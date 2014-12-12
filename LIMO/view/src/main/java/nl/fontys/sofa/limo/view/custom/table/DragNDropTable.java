package nl.fontys.sofa.limo.view.custom.table;

import javax.swing.DropMode;
import javax.swing.JTable;

/**
 * This class is almost equal to a JTable, it just offers the Drag 'N Drop
 * functionality.
 *
 * @author Matthias Brück
 */
public class DragNDropTable extends JTable implements Reorderable {

    /**
     * Creates a new DragNDropTable, which offers Drag 'N Drop functionality.
     *
     * @param dm The TableModel that should be used with this table.
     */
    public DragNDropTable(DragNDropTableModel dm) {
        super(dm);
        this.setDragEnabled(true);
        this.setDropMode(DropMode.INSERT_ROWS);
        this.setDropTarget(this.getDropTarget());
        this.setTransferHandler(new TableRowTransferHandler(this));
    }

    @Override
    public void reorder(int fromIndex, int toIndex) {
        Object[] draggedRow = getRowAt(fromIndex);
        if (fromIndex > toIndex) {
            for (int row = fromIndex - 1; row >= toIndex; row--) {
                Object[] movingRow = getRowAt(row);
                setRowAt(row + 1, movingRow);
            }
            setRowAt(toIndex, draggedRow);
        } else if (toIndex > fromIndex) {
            toIndex--;
            for (int row = fromIndex + 1; row <= toIndex; row++) {
                Object[] movingRow = getRowAt(row);
                setRowAt(row - 1, movingRow);
            }
            setRowAt(toIndex, draggedRow);
        }
        this.repaint();
    }

    /**
     * Tries to find a row at the given position. If the row is not available it
     * will return an empty Object Array.
     *
     * @param row The number of the row that has to be returned.
     * @return An empty Object Array if the row could not get found, otherwise
     * the row.
     */
    private Object[] getRowAt(int row) {
        Object[] result = new Object[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++) {
            result[i] = getModel().getValueAt(row, i);
        }
        return result;
    }

    /**
     * Tries to set a row at a specific position.
     *
     * @param row The rownumber where the values has to be set.
     * @param values The values that should be displayed in the specific row.
     */
    private void setRowAt(int row, Object[] values) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (values.length > i) {
                setValueAt(values[i], row, i);
            } else {
                setValueAt("", row, i);
            }
        }
    }
}
