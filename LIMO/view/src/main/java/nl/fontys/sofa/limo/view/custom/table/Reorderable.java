package nl.fontys.sofa.limo.view.custom.table;

/**
 * This interface is for Drag N Drop usage. It provides the class with the
 * "reorder" function.
 *
 * @author Matthias Brück
 */
public interface Reorderable {

    /**
     * Calling this method will reorder a row from a specified position to an
     * other one.
     *
     * @param fromIndex The row that has to be moved.
     * @param toIndex The position where the row has to be moved to.
     */
    public void reorder(int fromIndex, int toIndex);
}
