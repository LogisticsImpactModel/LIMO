package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.Entry;

public class MultiModeLeg extends Leg {

    private Map<Leg, Double> sublegs;

    public MultiModeLeg() {
        sublegs = new HashMap<>();
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

    @Override
    public Component copy() {
        MultiModeLeg copied = new MultiModeLeg();
        ArrayList<Entry> costsList = new ArrayList<>();
        for (Entry cost : costs) {
            costsList.add(cost.copy());
        }
        copied.setCosts(costsList);
        ArrayList<Entry> delaysList = new ArrayList<>();
        for (Entry delay : delays) {
            delaysList.add(delay.copy());
        }
        copied.setDelays(delaysList);
        copied.setDescription(description);
        copied.setIdentifier(identifier);
        copied.setLastUpdate(lastUpdate);
        ArrayList<Entry> leadTimesList = new ArrayList<>();
        for (Entry leadtime : leadTimes) {
            leadTimesList.add(leadtime.copy());
        }
        copied.setLeadTimes(leadTimesList);
        HashMap<Leg, Double> sublegsMap = new HashMap<>();
        for ( Map.Entry<Leg, Double> subleg : sublegs.entrySet()) {
            sublegsMap.put((Leg)subleg.getKey().copy(), subleg.getValue());
        }
        copied.setSublegs(sublegsMap);
        return copied;
    }
}
