package nl.fontys.sofa.limo.domain.component.leg;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Embedded;

/**
 * Multi mode leg which consists of multiple legs, each with a weight for probability of the
 * specific leg to be used.
 *
 * @author Dominik Kaisers <d.kaisers@student.fontys.nl>
 */
public class MultiModeLeg extends Leg {
    
    @Embedded
    private Map<Leg, Double> legs;

    public MultiModeLeg() {
        legs = new HashMap<>();
    }

    public Map<Leg, Double> getLegs() {
        return legs;
    }

    public void setLegs(Map<Leg, Double> legs) {
        this.legs = legs;
    }

}
