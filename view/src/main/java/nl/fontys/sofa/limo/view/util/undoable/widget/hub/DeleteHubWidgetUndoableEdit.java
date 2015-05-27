/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util.undoable.widget.hub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Pair;

/**
 *
 * @author Christina Zenzes
 */
public class DeleteHubWidgetUndoableEdit extends AbstractUndoableEdit {

    private ChainGraphScene scene;
    private HubNode node;
    private HubWidget widget;
    private boolean isStartHub;
    private HashMap<AbstractBeanNode, Pair<Widget, Widget>> legs;
    private List<UndoableEdit> childUndoableEdits;

    public DeleteHubWidgetUndoableEdit(ChainGraphScene scene, HubWidget widget) {
        this.scene = scene;
        this.widget = widget;

        this.node = (HubNode) scene.findObject(widget);
        this.isStartHub = scene.getStartWidget() == widget;
        initChildUndoableEdit();
    }

    private void initChildUndoableEdit() {
        legs = new HashMap<>();
        List<AbstractBeanNode> edges = (List<AbstractBeanNode>) scene.findNodeEdges(node, true, true);
        for (AbstractBeanNode edge : edges) {
            HubWidget source = (HubWidget) scene.findWidget(scene.getEdgeSource(edge));
            HubWidget target = (HubWidget) scene.findWidget(scene.getEdgeTarget(edge));
            LegWidget leg = (LegWidget) scene.findWidget(edge);
            if (source == null || target == null) {
                break;
            }
            Pair pair = Pair.of(source, target);
            legs.put(edge, pair);
        }
    }

    @Override
    public void undo() throws CannotRedoException {
        HubWidget oldWidget = widget;
        widget = (HubWidget) scene.addNode(node);
        widget.setPreferredLocation(oldWidget.getLocation());
        scene.addHubWidget(widget);
        if (isStartHub) {
            scene.setStartWidget(widget);
            widget.setStartFlag(true);
        }

        for (Map.Entry<AbstractBeanNode, Pair<Widget, Widget>> leg : legs.entrySet()) {
            HubWidget source =  (HubWidget) (leg.getValue().first() == oldWidget ? widget : leg.getValue().first()) ;
            HubWidget target = (HubWidget) (leg.getValue().second() == oldWidget ? widget : leg.getValue().second()) ;
            LegWidget legWidget = (LegWidget) scene.addEdge(leg.getKey());
            scene.connectHubWidgets(source, legWidget, target);
        }
        scene.validate();
        initChildUndoableEdit();
    }

    @Override
    public void redo() throws CannotUndoException {
        scene.removeHubWidget(widget);
        scene.removeNodeWithEdges(node);
        scene.validate();
    }

    @Override
    public boolean canRedo() {
        return true;
    }

}
