package nl.fontys.sofa.limo.domain.component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias Brück
 */
public class MultiModeLeg extends Leg{

    private final HashMap<Leg, Double> sublegs;

    public MultiModeLeg(String identifier) {
        super(identifier);
        sublegs = new HashMap<>();
    }

    public Map<Leg, Double> getSublegs() {
        return sublegs;
    }
}