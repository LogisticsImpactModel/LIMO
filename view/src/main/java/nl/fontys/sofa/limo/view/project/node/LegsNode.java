/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sofa.limo.view.project.node;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

/**
 *
 * @author nilsh
 */
public class LegsNode extends AbstractNode {

    public LegsNode(Children children) {
        super(children);
    }

    @Override
    public String getName() {
        return "Legs";
    }

}
