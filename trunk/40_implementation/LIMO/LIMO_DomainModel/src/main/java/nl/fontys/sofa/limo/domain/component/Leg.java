package nl.fontys.sofa.limo.domain.component;

import nl.fontys.sofa.limo.domain.Actor;
import nl.fontys.sofa.limo.domain.Component;

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
    }

    public Hub getEndHub() {
        return endHub;
    }

    public void setEndHub(Hub endHub) {
        this.endHub = endHub;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }
    // </editor-fold>
}
