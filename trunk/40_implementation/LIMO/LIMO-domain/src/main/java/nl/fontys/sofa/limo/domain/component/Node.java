package nl.fontys.sofa.limo.domain.component;

/**
 * Node is a graph node. Each hub and leg is part of a graph with a next and previous node. A hub
 * can only have legs as next and previous nodes, while a leg can only have hubs.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 * @param <T> Type of Node. Leg for Hubs and Hub for Legs.
 */
public abstract class Node<T extends Node> extends Component {
    
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
