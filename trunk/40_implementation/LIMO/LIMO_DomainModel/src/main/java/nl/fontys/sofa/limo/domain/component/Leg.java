package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;
import nl.fontys.sofa.limo.domain.Entry;

public class Leg extends Component {

    private Hub startHub;
    private Hub endHub;
    private Actor actor;

    public Leg() {
    }

    public Leg(String identifier) {
        super(identifier);
    }

    // <editor-fold defaultstate="collapsed" desc=" ${GETTERS AND SETTERS} ">
    public Hub getStartHub() {
        return startHub;
    }

    public void setStartHub(Hub startHub) {
        this.startHub = startHub;
        if (startHub.getInputLeg() == null || !startHub.getOutputLeg().equals(this)) {
            startHub.setOutputLeg(this);
        }
    }

    public Hub getEndHub() {
        return endHub;
    }

    public void setEndHub(Hub endHub) {
        this.endHub = endHub;
        if (endHub.getInputLeg() == null || !endHub.getInputLeg().equals(this)) {
            endHub.setInputLeg(this);
        }
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
    // </editor-fold>

    @Override
    public Component copy() {
        Leg copied = new Leg();
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
        return copied;
    }
}
