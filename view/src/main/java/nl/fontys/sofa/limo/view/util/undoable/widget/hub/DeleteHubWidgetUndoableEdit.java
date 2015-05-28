/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util.undoable.widget.hub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegWidget;
import org.openide.util.Pair;

/**
 * The {@link nl.fontys.sofa.limo.view.util.undoable.widget.hub.DeleteHubWidgetUndoableEdit}
 * provides undo and redo functionaltiy for when a HubWidget is delete.
 * 
 * @author Christina Zenzes
 */
public class DeleteHubWidgetUndoableEdit extends HubWidgetUndoableEdit {


    private HashMap<AbstractBeanNode, Pair<HubNode, HubNode>> legs;

    public DeleteHubWidgetUndoableEdit(ChainGraphScene scene, HubWidget widget) {
        super(scene, widget);
        initChildUndoableEdit();
    }
   

 
// The methods stores all needed information for recreating a LegWidget.
    private void initChildUndoableEdit() {
        legs = new HashMap<>();
        List<AbstractBeanNode> edges = (List<AbstractBeanNode>) scene.findNodeEdges(node, true, true);
        for (AbstractBeanNode edge : edges) {
            HubNode source = (HubNode) scene.getEdgeSource(edge);
            HubNode target = (HubNode) scene.getEdgeTarget(edge);

            if (source == null || target == null) {
                break;
            }
            Pair pair = Pair.of(source, target);
            legs.put(edge, pair);
        }
    }

    @Override
    public void undo() throws CannotRedoException {
      this.addHub();

        for (Map.Entry<AbstractBeanNode, Pair<HubNode, HubNode>> leg : legs.entrySet()) {
            HubWidget source = (HubWidget) scene.findWidget(leg.getValue().first());
            HubWidget target = (HubWidget) scene.findWidget(leg.getValue().second());
            LegWidget legWidget = (LegWidget) scene.addEdge(leg.getKey());
            scene.connectHubWidgets(source, legWidget, target);
        }
        scene.validate();
    }

    @Override
    public void redo() throws CannotUndoException {
        this.removeHub();
    }


}
