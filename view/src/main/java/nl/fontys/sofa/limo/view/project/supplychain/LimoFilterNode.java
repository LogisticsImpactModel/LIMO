/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.supplychain;

import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import nl.fontys.sofa.limo.domain.component.event.Event;
import nl.fontys.sofa.limo.domain.component.procedure.Procedure;
import nl.fontys.sofa.limo.view.node.bean.EventNode;
import nl.fontys.sofa.limo.view.node.bean.HubNode;
import nl.fontys.sofa.limo.view.node.bean.LegNode;
import nl.fontys.sofa.limo.view.node.bean.ProcedureNode;
import nl.fontys.sofa.limo.view.project.node.EventProjectNode;
import nl.fontys.sofa.limo.view.project.node.ProcedureProjectNode;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

/**
 *
 * @author nilsh
 */
public class LimoFilterNode extends FilterNode implements PropertyChangeListener {

    private final Node original;

    public LimoFilterNode(Node original) {
        super(original);
        this.original = original;
        addChangelistener(original);

    }

    private void addChangelistener(Node original1) {
        if (this.original instanceof HubNode) {
            HubNode hub = (HubNode) original1;
            hub.getHub().addPropertyChangeListener(this);
        }
        if (this.original instanceof LegNode) {
            LegNode leg = (LegNode) original1;
            leg.getLeg().addPropertyChangeListener(this);
        }
    }

    public LimoFilterNode(Node original, org.openide.nodes.Children children) {
        super(original, children);
        this.original = original;
        addChangelistener(original);

    }

    public LimoFilterNode(Node original, org.openide.nodes.Children children, Lookup lookup) {
        super(original, children, lookup);
        this.original = original;
        addChangelistener(original);
    }

    @Override
    public Node getOriginal() {
        return original;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ("ADD_EVENT"): {
                try {
                    Node child = this.getChildren().findChild("Events");
                    Event event = (Event) evt.getNewValue();
                    EventNode node = new EventProjectNode(event);
                    Node[] n = {node};
                    child.getChildren().add(n);
                    return;
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            case ("REMOVE_EVENT"): {
                Node child = this.getChildren().findChild("Events");
                org.openide.nodes.Children children = child.getChildren();
                Event e = (Event) evt.getOldValue();
                for (Node n : children.snapshot()) {
                    EventNode en = (EventNode) n;
                    if (en.getEvent().equals(e)) {
                        Node[] no = {en};
                        children.remove(no);
                    }
                }
            }
            case ("ADD_PROCEDURE"): {
                try {
                    Node child = this.getChildren().findChild("Procedures");
                    Procedure procedure = (Procedure) evt.getNewValue();
                    ProcedureNode node = new ProcedureProjectNode(procedure);
                    Node[] n = {node};
                    child.getChildren().add(n);
                    return;
                } catch (IntrospectionException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            case ("REMOVE_PROCEDURE"): {
                Node child = this.getChildren().findChild("Procedures");
                org.openide.nodes.Children children = child.getChildren();
                Procedure p = (Procedure) evt.getOldValue();
                for (Node n : children.snapshot()) {
                    ProcedureNode pn = (ProcedureNode) n;
                    if (pn.getProcedure().equals(p)) {
                        Node[] no = {pn};
                        children.remove(no);
                    }
                }
            }

        }
    }

}
