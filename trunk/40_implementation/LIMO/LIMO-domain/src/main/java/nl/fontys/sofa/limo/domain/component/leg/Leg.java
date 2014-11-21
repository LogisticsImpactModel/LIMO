package nl.fontys.sofa.limo.domain.component.leg;

import java.util.HashMap;
import java.util.Map;
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

    public Map<Leg, Double> getLegs() {
        Map<Leg, Double> legs = new HashMap();
        legs.put(this, 100.0);
        return legs;
    }

}
