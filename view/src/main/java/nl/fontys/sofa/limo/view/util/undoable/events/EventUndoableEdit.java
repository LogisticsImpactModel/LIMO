/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.util.undoable.events;

import java.util.List;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import nl.fontys.sofa.limo.domain.component.Component;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.view.chain.ChainGraphScene;
import nl.fontys.sofa.limo.view.node.bean.AbstractBeanNode;
import org.netbeans.api.visual.widget.LabelWidget;

/**
 *
 * @author Christina Zenzes
 */
public class EventUndoableEdit extends AbstractUndoableEdit {

    private List<Event> oldEvents;
    private final ChainGraphScene scene;
    private final AbstractBeanNode bean;
    private final LabelWidget updateLabelWigdet;

    public EventUndoableEdit(AbstractBeanNode bean, ChainGraphScene scene, LabelWidget updateLabelWidget) {
        this.oldEvents = (List<Event>) bean.getValue("events");
        this.bean = bean;
        this.scene = scene;
        this.updateLabelWigdet = updateLabelWidget;

    }

    @Override
    public void undo() throws CannotUndoException {
        toggleEvents();

    }

    @Override
    public void redo() throws CannotRedoException {
        toggleEvents();
    }

    private void toggleEvents() {
        Component component = bean.getLookup().lookup(Component.class);
        List<Event> events = component.getEvents();
        component.setEvents(oldEvents);
         if (oldEvents.isEmpty()) {
            updateLabelWigdet.setLabel("");
        } else {
            updateLabelWigdet.setLabel("Events: " + oldEvents.size());
        }
        oldEvents = events;
        bean.setValue("events", oldEvents);
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
