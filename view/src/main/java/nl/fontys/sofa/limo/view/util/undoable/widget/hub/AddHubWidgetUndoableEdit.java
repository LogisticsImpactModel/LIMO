package nl.fontys.sofa.limo.view.util.undoable.widget.hub;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.widget.HubWidget;

/**
 * The
 * {@link nl.fontys.sofa.limo.view.util.undoable.widget.hub.AddHubWidgetUndoableEdit}
 * provides undo and redo functionaltiy for when a HubWidget is added.
 *
 * @author Christina Zenzes
 */
public class AddHubWidgetUndoableEdit extends HubWidgetUndoableEdit {

    public AddHubWidgetUndoableEdit(ChainGraphScene scene, HubWidget widget) {
        super(scene, widget);
    }

    @Override
    public void redo() throws CannotRedoException {
        this.addHub();
    }

    @Override
    public void undo() throws CannotUndoException {
        this.removeHub();
    }

}
