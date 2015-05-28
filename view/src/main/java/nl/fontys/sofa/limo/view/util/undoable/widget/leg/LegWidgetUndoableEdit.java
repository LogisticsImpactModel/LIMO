package nl.fontys.sofa.limo.view.util.undoable.widget.leg;

import javax.swing.undo.AbstractUndoableEdit;
import nl.fontys.sofa.limo.domain.component.leg.Leg;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.node.bean.LegNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;
import nl.fontys.sofa.limo.view.widget.LegWidget;

/**
 * The
 * {@link nl.fontys.sofa.limo.view.util.undoable.widget.leg.LegWidgetUndoableEdit}
 * class is an abstract class which provides methods for creating or deleting a
 * leg to its child classes. Beyond that, the class extends the
 * {@link javax.swing.undo.AbstractUndoableEdit} class. The child objects of
 * this class are meant to be added to an {@link javax.swing.undo.UndoManager}.
 *
 *
 *
 * @author Christina Zenzes
 */
public abstract class LegWidgetUndoableEdit extends AbstractUndoableEdit {

    private LegNode legNode;
    private HubNode source;
    private HubNode target;
    private ChainGraphScene scene;

    public LegWidgetUndoableEdit(LegWidget legWidget, HubNode source, HubNode target, ChainGraphScene scene) {
        this.scene = scene;
        this.legNode = (LegNode) scene.findObject(legWidget);
        this.source = source;
        this.target = target;
    }

    protected void addLegWidget() {
        LegWidget leg = (LegWidget) scene.addEdge(legNode);
        scene.connectHubWidgets(
                (HubWidget) scene.findWidget(source),
                leg,
                (HubWidget) scene.findWidget(target));
        scene.validate();
    }

    protected void removeLegWidget() {
        scene.getChainBuilder().disconnectLeg(legNode.getLookup().lookup(Leg.class));
        scene.removeEdge(legNode);
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
