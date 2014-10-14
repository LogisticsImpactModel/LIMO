package nl.fontys.sofa.limo.domain.component.leg;

import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.hub.Hub;

/**
 * Leg of supply chain.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Leg extends Node<Hub> {
    
    private Icon icon;

    public Leg() {
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    
}
