package nl.fontys.sofa.limo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nl.fontys.sofa.limo.domain.component.Node;

/**
 * An actor is a company or individual holding responsible for the consigment over the course of
 * the nodes specified in the actors list. Every node in a supply chain belongs to an actor (at 
 * least conceptually).
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Actor implements Serializable {
    
    private String name;
    private List<Node> nodes;

    public Actor() {
        this.nodes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    
}
