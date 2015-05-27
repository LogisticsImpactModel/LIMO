/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.sofa.limo.view.util.undoable.widget.leg;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.LegNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegWidget;

/**
 *
 * @author Christina Zenzes
 */
public class AddLegWidgetUndoableEdit extends AbstractUndoableEdit{
    
    private LegNode legNode;
    private HubWidget source;
    private HubWidget target;
    private ChainGraphScene scene;

    public AddLegWidgetUndoableEdit(LegWidget legWidget, HubWidget source, HubWidget target,ChainGraphScene scene) {       
        this.scene = scene;     
        this.legNode = (LegNode) scene.findObject(legWidget);
        this.source = source;
        this.target = target;
    }

    @Override
    public boolean canRedo() {
        return true;
    }
    
    

    @Override
    public void redo() throws CannotRedoException {
        LegWidget leg = (LegWidget) scene.addEdge(legNode);
                scene.connectHubWidgets(
                        source,
                        leg,
                        target);
                scene.validate();
    }

    @Override
    public void undo() throws CannotUndoException {
        scene.getChainBuilder().disconnectLeg(legNode.getLookup().lookup(Leg.class));
                scene.removeEdge(legNode);
                scene.validate();
    }

   
    
    
    
    
    
    
    
}
