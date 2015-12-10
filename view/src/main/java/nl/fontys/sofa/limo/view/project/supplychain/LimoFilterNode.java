/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.supplychain;

import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 *
 * @author nilsh
 */
public class LimoFilterNode extends FilterNode {

    private final Node original;

    public LimoFilterNode(Node original) {
        super(original);
        this.original = original;
    }

    public LimoFilterNode(Node original, org.openide.nodes.Children children) {
        super(original, children);
        this.original = original;
    }

    public LimoFilterNode(Node original, org.openide.nodes.Children children, Lookup lookup) {
        super(original, children, lookup);
        this.original = original;
    }

    @Override
    public Node getOriginal() {
        return original;
    }

}
