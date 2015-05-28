package nl.fontys.sofa.limo.view.util.undoable.widget.hub;

import java.awt.Point;
import javax.swing.undo.AbstractUndoableEdit;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.widget.HubWidget;

/**
 * The
 * {@link nl.fontys.sofa.limo.view.util.undoable.widget.hub.HubWidgetUndoableEdit}
 * class is an abstract class which provides methods for creating or deleting a
 * hub to its child classes. Beyond that, the class extends the
 * {@link javax.swing.undo.AbstractUndoableEdit} class. The child objects of
 * this class are meant to be added to an {@link javax.swing.undo.UndoManager}.
 *
 *
 * @author Christina Zenzes
 */
public abstract class HubWidgetUndoableEdit extends AbstractUndoableEdit {

    protected ChainGraphScene scene;
    HubNode node;
    private HubWidget widget;
    private boolean isStartHub;

    public HubWidgetUndoableEdit(ChainGraphScene scene, HubWidget widget) {
        this.scene = scene;
        this.widget = widget;

        this.node = (HubNode) scene.findObject(widget);
        this.isStartHub = scene.getStartWidget() == widget;

    }

    protected void addHub() {
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

    protected void removeHub() {
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
