/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util.undoable.widget.hub;

import java.awt.Point;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;

/**
 *
 * @author Christina Zenzes
 */
public class AddHubWidgetUndoableEdit extends AbstractUndoableEdit {

    private ChainGraphScene scene;
    private HubNode node;
    private HubWidget widget;
    private boolean isStartHub;

    public AddHubWidgetUndoableEdit(ChainGraphScene scene, HubWidget widget) {
        this.scene = scene;
        this.widget = widget;

        this.node = (HubNode) scene.findObject(widget);
        this.isStartHub = scene.getStartWidget() == widget;
        
    }

    @Override
    public void redo() throws CannotRedoException {
        Point location = widget.getLocation();
        widget = (HubWidget) scene.addNode(node);
        widget.setPreferredLocation(location);
        scene.addHubWidget(widget);
        if (isStartHub) {
            scene.setStartWidget(widget);
            widget.setStartFlag(true);
        }
        scene.validate();
       
    }

    @Override
    public void undo() throws CannotUndoException {
        scene.removeHubWidget(widget);
        scene.removeNodeWithEdges(node);
        scene.validate();
    }

    @Override
    public boolean canRedo() {
        return true;
    }

    @Override
    public boolean canUndo() {
        return true;
    }
    
    

}
