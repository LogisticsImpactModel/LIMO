package nl.fontys.sofa.limo.view.util.undoable.widget.leg;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.widget.LegWidget;

/**
 * The
 * {@link nl.fontys.sofa.limo.view.util.undoable.widget.leg.DeleteLegWidgetUndoableEdit}
 * provides undo and redo functionaltiy for when a LegWidget is deleted.
 *
 * @author Christina Zenzes
 */
public class DeleteLegWidgetUndoableEdit extends LegWidgetUndoableEdit {

    public DeleteLegWidgetUndoableEdit(LegWidget legWidget, HubNode source, HubNode target, ChainGraphScene scene) {
        super(legWidget, source, target, scene);
    }

    @Override
    public void redo() throws CannotUndoException {
        this.removeLegWidget();
    }

    @Override
    public void undo() throws CannotRedoException {
        this.addLegWidget();
    }

}
