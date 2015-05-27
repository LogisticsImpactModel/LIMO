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
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.node.bean.LegNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegWidget;

/**
 *
 * @author Christina Zenzes
 */
public class AddLegWidgetUndoableEdit extends AbstractUndoableEdit {

    private LegNode legNode;
    private HubNode source;
    private HubNode target;
    private ChainGraphScene scene;

    public AddLegWidgetUndoableEdit(LegWidget legWidget, HubNode source, HubNode target, ChainGraphScene scene) {
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
                (HubWidget) scene.findWidget(source),
                leg,
                (HubWidget) scene.findWidget(target));
        scene.validate();
    }

    @Override
    public void undo() throws CannotUndoException {
        scene.getChainBuilder().disconnectLeg(legNode.getLookup().lookup(Leg.class));
        scene.removeEdge(legNode);
        scene.validate();
    }

}
