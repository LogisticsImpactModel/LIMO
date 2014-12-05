package nl.fontys.sofa.limo.view.custom.table;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSource;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

/**
 * A TableRowTransferHandler that handles Drag{@literal &}Drop in tables.
 *
 * @author Matthias Brück
 */
public class TableRowTransferHandler extends TransferHandler {

    private final DataFlavor localObjectFlavor = new DataFlavor(Integer.class, "Integer Row Index");
    private final JTable table;

    public TableRowTransferHandler(JTable table) {
        this.table = table;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        assert (c == table);
        return new DataHandler(table.getSelectedRow(), localObjectFlavor.getMimeType());
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport info) {
        boolean isImportable = info.getComponent() == table && info.isDrop() && info.isDataFlavorSupported(localObjectFlavor);
        table.setCursor(isImportable ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
        return isImportable;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport info) {
        JTable target = (JTable) info.getComponent();
        JTable.DropLocation dropLocation = (JTable.DropLocation) info.getDropLocation();
        int dropLocationIndex = dropLocation.getRow();
        int maxNumberOfRows = table.getModel().getRowCount();
        if (dropLocationIndex < 0 || dropLocationIndex > maxNumberOfRows) {
            dropLocationIndex = maxNumberOfRows;
        }
        target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        try {
            Integer dragRowIndex = (Integer) info.getTransferable().getTransferData(localObjectFlavor);
            if (dragRowIndex != -1 && dragRowIndex != dropLocationIndex) {
                ((Reorderable) table).reorder(dragRowIndex, dropLocationIndex);
                if (dropLocationIndex > dragRowIndex) {
                    dropLocationIndex--;
                }
                target.getSelectionModel().addSelectionInterval(dropLocationIndex, dropLocationIndex);
                return true;
            }
        } catch (UnsupportedFlavorException | IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    protected void exportDone(JComponent c, Transferable t, int act) {
        if (act == TransferHandler.MOVE) {
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
