package nl.fontys.sofa.limo.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias Brück
 */
public class MultiModeLeg extends Leg{

    private final Map<Leg, Double> sublegs;

    public MultiModeLeg(String identifier) {
        super(identifier);
        sublegs = new HashMap<>();
    }

    public Map<Leg, Double> getSublegs() {
        return sublegs;
    }
}