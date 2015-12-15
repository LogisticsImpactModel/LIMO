package nl.fontys.sofa.limo.domain.component;

import com.google.gson.annotations.Expose;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;

/**
 * Node is a graph node. Each hub and leg is part of a graph with a next and
 * previous node. A hub can only have legs as next and previous nodes, while a
 * leg can only have hubs.
 *
 * @author Dominik Kaisers {@literal <d.kaisers@student.fontys.nl>}
 * @param <T> Type of Node. Leg for Hubs and Hub for Legs.
 */
public abstract class Node<T extends Node> extends Component {

    private static final long serialVersionUID = -7756347619644993900L;

    @Expose
    protected T next;
    protected T previous;

    public Node() {
        super();
    }

    public T getNext() {
        return next;
    }

    public void setNext(T next) {
        this.next = next;
        if (this.next.previous == null || !this.next.previous.equals(this)) {
            this.next.previous = this;
        }
        firePropertyChange();
    }

    /**
     * Remove getNext() object from this object. This method also removes the
     * getPrevious() object of the next object.
     */
    public void removeNext() {
        this.next.previous = null;
        this.next = null;
        firePropertyChange();
    }

    /**
     * Remove getPrevious() object from this object. This method also removes
     * the getNext() object of the previous object.
     */
    public void removePrevious() {
        this.previous.next = null;
        this.previous = null;
        firePropertyChange();
    }

    public T getPrevious() {
        return previous;
    }

    public void setPrevious(T previous) {
        this.previous = previous;
        if (this.previous.next == null || !this.previous.next.equals(this)) {
            this.previous.next = this;
        }
        firePropertyChange();
    }

    @Override
    public void setDescription(String description) {
        super.setDescription(description); //To change body of generated methods, choose Tools | Templates.
        firePropertyChange();
    }

    @Override
    public void setEvents(List<Event> events) {
        super.setEvents(events); //To change body of generated methods, choose Tools | Templates.
        firePropertyChange();
    }

    @Override
    public void setId(String id) {
        super.setId(id); //To change body of generated methods, choose Tools | Templates.
        firePropertyChange();
    }

    @Override
    public void setLastUpdate(long lastUpdate) {
        super.setLastUpdate(lastUpdate); //To change body of generated methods, choose Tools | Templates.
        firePropertyChange();
    }

    @Override
    public void setName(String name) {
        super.setName(name); //To change body of generated methods, choose Tools | Templates.
        firePropertyChange();
    }

    @Override
    public void setProcedures(List<Procedure> procedures) {
        super.setProcedures(procedures); //To change body of generated methods, choose Tools | Templates.
        firePropertyChange();
    }

    @Override
    public void setUniqueIdentifier(String uniqueIdentifier) {
        super.setUniqueIdentifier(uniqueIdentifier); //To change body of generated methods, choose Tools | Templates.
        firePropertyChange();
    }

}
