/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util.undoable.widget.hub;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.widget.HubWidget;

/**
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
