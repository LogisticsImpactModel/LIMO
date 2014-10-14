package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.Entry;
import nl.fontys.sofa.limo.domain.location.Location;

public class Hub extends Component {

    private Leg inputLeg;
    private Leg outputLeg;
    private Map<Entry, Actor> costResponsibilities;
    private Location location;

    public Hub() {
    }

    public Hub(String identifier, Location location) {
        super(identifier);
        this.costResponsibilities = new HashMap<>();
        this.location = location;
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
    public Location getLocation() {
        return location;
    }

    public Leg getInputLeg() {
        return inputLeg;
    }

    public Leg getOutputLeg() {
        return outputLeg;
    }

    public Map<Entry, Actor> getCostResponsibilities() {
        return costResponsibilities;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void setCostResponsibilities(Map<Entry, Actor> costResponsibilities) {
        this.costResponsibilities = costResponsibilities;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setInputLeg(Leg inputLeg) {
        this.inputLeg = inputLeg;
        if (inputLeg.getStartHub() == null || !inputLeg.getEndHub().equals(this)) {
            inputLeg.setEndHub(this);
        }
    }

    public void setOutputLeg(Leg outputLeg) {
        this.outputLeg = outputLeg;
        if (outputLeg.getStartHub() == null || !outputLeg.getStartHub().equals(this)) {
            outputLeg.setStartHub(this);
        }
    }
    // </editor-fold>

    @Override
    public Component copy() {
        Hub copied = new Hub();
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
        ArrayList<Event> eventsList = new ArrayList<>();
        for (Event event : events) {
            eventsList.add(event.copy());
        }
        copied.setEvents(eventsList);
        copied.setIcon(icon.copy());
        copied.setIdentifier(identifier);
        copied.setLastUpdate(lastUpdate);
        ArrayList<Entry> leadTimesList = new ArrayList<>();
        for (Entry leadtime : leadTimes) {
            leadTimesList.add(leadtime.copy());
        }
        copied.setLeadTimes(leadTimesList);
        copied.setLocation(location.copy());
        return copied;
    }
}
