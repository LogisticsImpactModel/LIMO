package nl.fontys.sofa.limo.view.custom.table;

import javax.swing.DropMode;
import javax.swing.JTable;

/**
 *
 * @author Matthias Brück
 */
public class DragNDropTable extends JTable implements Reorderable {

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

    private Object[] getRowAt(int row) {
        Object[] result = new Object[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++) {
            result[i] = getModel().getValueAt(row, i);
        }
        return result;
    }

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
