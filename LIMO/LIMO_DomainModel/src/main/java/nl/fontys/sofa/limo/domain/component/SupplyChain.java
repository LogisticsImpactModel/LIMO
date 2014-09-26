package nl.fontys.sofa.limo.domain.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nl.fontys.sofa.limo.domain.Actor;

/**
 *
 * @author Matthias Brück
 */
public class SupplyChain {

    private String name;
    private Hub startHub;

    public SupplyChain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Hub getStartHub() {
        return startHub;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Leg getLeg(String identifier) {
        for (Leg leg : getAllLegs()) {
            if (leg.getIdentifier().equals(identifier)) {
                return leg;
            }
        }
        return null;
    }

    public Hub getHub(String identifier) {
        for (Hub hub : getAllHubs()) {
            if (hub.getIdentifier().equals(identifier)) {
                return hub;
            }
        }
        return null;
    }

    public List<Leg> getAllLegs() {
        if (startHub == null) {
            return new ArrayList<>();
        }
        ArrayList<Leg> legs = new ArrayList<>();
        Hub hub = startHub;
        while (hub.getOutputLeg() != null) {
            legs.add(hub.getOutputLeg());
            if (hub.getOutputLeg().getEndHub() == null) {
                break;
            }
            hub = hub.getOutputLeg().getEndHub();
        }
        return legs;
    }

    public List<Hub> getAllHubs() {
        if (startHub == null) {
            return new ArrayList<>();
        }
        ArrayList<Hub> hubs = new ArrayList<>();
        Hub hub = startHub;
        hubs.add(hub);
        while (hub.getOutputLeg() != null) {
            if (hub.getOutputLeg().getEndHub() == null) {
                break;
            }
            hub = hub.getOutputLeg().getEndHub();
            hubs.add(hub);
        }
        return hubs;
    }

    public List<Actor> getAllActors() {
        HashMap<Actor, Boolean> map = new HashMap<>();
        for (Hub hub : getAllHubs()) {
            for (Actor actor : hub.getCostResponsibilities().values()) {
                map.put(actor, true);
            }
        }
        for (Leg leg : getAllLegs()) {
            map.put(leg.getActor(), true);
        }
        ArrayList<Actor> actors = new ArrayList<>();
        actors.addAll(map.keySet());
        return actors;
    }
}
