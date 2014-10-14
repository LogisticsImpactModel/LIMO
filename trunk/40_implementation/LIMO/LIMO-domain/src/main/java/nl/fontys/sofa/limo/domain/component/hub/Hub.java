package nl.fontys.sofa.limo.domain.component.hub;

import javax.persistence.Embedded;
import nl.fontys.sofa.limo.domain.component.Icon;
import nl.fontys.sofa.limo.domain.component.Node;
import nl.fontys.sofa.limo.domain.component.leg.Leg;

/**
 * Hub of a supply chain.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class Hub extends Node<Leg> {
    
    @Embedded
    private Location location;
    @Embedded
    private Icon icon;

    public Hub() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    
}
