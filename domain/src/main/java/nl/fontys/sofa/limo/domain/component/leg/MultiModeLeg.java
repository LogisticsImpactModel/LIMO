package nl.fontys.sofa.limo.domain.component.leg;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Embedded;

/**
 * Multi mode leg which consists of multiple legs, each with a weight for
 * probability of the specific leg to be used.
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

    /**
     * Sums the weight values of all legs in the leg list (legs)
     *
     * @return - sum of weights
     */
    public double getTotalWeight() {
        double sum = 0;
        for (Double weight : legs.values()) {
            sum += weight;
        }
        return sum;
    }

    /**
     * Adds a leg with its weight to the list of legs
     *
     * @param leg - leg object
     * @param weight - how likely it is for one option of the multimode leg to
     * be chosen. More weight is higher likeliness. Like a probability.
     */
    public void addLeg(Leg leg, double weight) {
        legs.put(leg, weight);
    }
}
