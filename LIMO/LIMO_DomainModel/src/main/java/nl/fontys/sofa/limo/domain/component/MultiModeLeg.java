package nl.fontys.sofa.limo.domain.component;

import java.util.HashMap;
import java.util.Map;

public class MultiModeLeg extends Leg {

    private Map<Leg, Double> sublegs;

    public MultiModeLeg() {
    }

    public MultiModeLeg(String identifier) {
        super(identifier);
        sublegs = new HashMap<>();
    }

    public MultiModeLeg(Map<Leg, Double> sublegs, String identifier) {
        super(identifier);
        this.sublegs = sublegs;
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
    public Map<Leg, Double> getSublegs() {
        return sublegs;
    }

    public void setSublegs(Map<Leg, Double> sublegs) {
        this.sublegs = sublegs;
    }
    // </editor-fold>
}
