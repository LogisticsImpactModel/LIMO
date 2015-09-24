package nl.fontys.sofa.limo.domain.component;

import com.google.gson.annotations.Expose;

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

    @Expose protected T next;
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
    }

    /**
     * Remove getNext() object from this object. This method also removes the
     * getPrevious() object of the next object.
     */
    public void removeNext() {
        this.next.previous = null;
        this.next = null;
    }

    /**
     * Remove getPrevious() object from this object. This method also removes the
     * getNext() object of the previous object.
     */
    public void removePrevious() {
        this.previous.next = null;
        this.previous = null;
    }

    public T getPrevious() {
        return previous;
    }

    public void setPrevious(T previous) {
        this.previous = previous;
        if (this.previous.next == null || !this.previous.next.equals(this)) {
            this.previous.next = this;
        }
    }

}
