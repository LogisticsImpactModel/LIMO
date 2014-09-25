package nl.fontys.sofa.limo.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthias Brück
 */
public class Actor {

    private final String name;
    private final List<Leg> legsResponsibleFor;

    public Actor(String name) {
        this.name = name;
        legsResponsibleFor = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<Leg> getLegsResponsibleFor() {
        return this.legsResponsibleFor;
    }
}