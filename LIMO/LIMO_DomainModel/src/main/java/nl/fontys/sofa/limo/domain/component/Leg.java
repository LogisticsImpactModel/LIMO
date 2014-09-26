package nl.fontys.sofa.limo.domain.component;

import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;

/**
 * @author Matthias Brück
 */
public class Leg extends Component {

    private Hub startHub;
    private Hub endHub;
    private Actor actor;

    public Leg(String identifier) {
        super(identifier);
    }

    public Hub getStartHub() {
        return this.startHub;
    }

    public Hub getEndHub() {
        return this.endHub;
    }

    public Actor getActor() {
        return this.actor;
    }

    public void setStartHub(Hub startHub) {
        this.startHub = startHub;
    }

    public void setEndHub(Hub endHub) {
        this.endHub = endHub;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
}
