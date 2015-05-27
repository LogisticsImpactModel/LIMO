/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util.undoable.widget.leg;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.widget.LegWidget;

/**
 *
 * @author Christina Zenzes
 */
public class AddLegWidgetUndoableEdit extends LegWidgetUndoableEdit {

    public AddLegWidgetUndoableEdit(LegWidget legWidget, HubNode source, HubNode target, ChainGraphScene scene) {
        super(legWidget, source, target, scene);
    }

    @Override
    public void redo() throws CannotRedoException {
        this.addLegWidget();
    }

    @Override
    public void undo() throws CannotUndoException {
        this.removeLegWidget();
    }

}
